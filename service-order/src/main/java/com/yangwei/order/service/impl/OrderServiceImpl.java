package com.yangwei.order.service.impl;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.yangwei.common.utils.StringUtil;
import com.yangwei.order.domain.OrderBasicInfo;
import com.yangwei.order.feign.GoodsService;
import com.yangwei.order.redlock.DistributedLocker;
import com.yangwei.order.repository.OrderBasicRepository;
import com.yangwei.order.service.OrderService;
import org.redisson.api.RLock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author 杨威
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Resource
    private OrderBasicRepository orderBasicRepository;
    @Resource
    private GoodsService goodsService;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private DistributedLocker distributedLocker;

    @Override
    @LcnTransaction
    public OrderBasicInfo createByOrderBasicInfo(OrderBasicInfo orderBasicInfo){
        //手动创建线程池
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("create-order-pool-%d").build();
        ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        //直接加锁，获取不到锁则一直等待获取锁，获得锁后一直不解锁则10秒后自动解锁
         RLock lock = distributedLocker.lock("order_key",10L);
        //先生成订单
        Future<OrderBasicInfo> future = cachedThreadPool.submit(() -> orderBasicRepository.save(orderBasicInfo));
        try {
            OrderBasicInfo orderInfo = future.get();
            System.out.println("创建一个订单 orderBasicInfo{}：" + orderInfo);
            if (!StringUtil.isEmpty(orderInfo.getOrderId())) {
            //生成订单改变库存数量
            goodsService.updateStocksNumberByGoodId(orderInfo.getGoodsId(), 10L);
            System.out.println("======获得锁后进行相应的操作======" + Thread.currentThread().getName() + "," + new Date());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            System.out.println("==============");
            cachedThreadPool.shutdown();
            distributedLocker.unlock(lock);
        }
        /*rabbitmq实现分布式订单生成完成后，把订单发送给修改库存队列
         this.sendOrderBasicInfo(orderInfo.getOrderId());*/
        return orderBasicInfo;
    }

    private void sendOrderBasicInfo(String orderId){
        //封装发送消息
        Map<String, String> orderMap = new HashMap<>(8);
        orderMap.put("orderId", orderId);
        ObjectMapper mapper = new ObjectMapper();
        String orderMsg;
        try {
            orderMsg = mapper.writeValueAsString(orderMap);
            Message message = MessageBuilder.withBody(orderMsg.getBytes()).setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .setContentEncoding("utf-8").setMessageId(orderId).build();
            // 构建回调返回的数据
            CorrelationData correlationData = new CorrelationData(orderId);
            // 发送消息
            this.rabbitTemplate.setMandatory(true);
            this.rabbitTemplate.setConfirmCallback(this);
            rabbitTemplate.convertAndSend("order_exchange", "orderRoutingKey", message, correlationData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        }

    @Override
    public OrderBasicInfo findByOrderId(String orderId) {
        return orderBasicRepository.findByOrderId(orderId);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String orderId = correlationData.getId();
        System.out.println("消息id:" + correlationData.getId());
        if (ack) {
            System.out.println("消息发送确认成功");
        } else {
            //重试机制
            sendOrderBasicInfo(orderId);
            System.out.println("消息发送确认失败:" + cause);
        }
    }
}
