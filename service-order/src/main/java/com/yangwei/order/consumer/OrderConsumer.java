package com.yangwei.order.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yangwei.order.domain.OrderBasicInfo;
import com.yangwei.order.repository.OrderBasicRepository;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 杨威
 */
@Component
public class OrderConsumer {
    @Resource
    private OrderBasicRepository orderBasicRepository;

    @RabbitListener(queues = "order_create_queue")
    public void process(Message message, Channel channel) throws Exception {
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("修改库存" + msg + ",消息id:" + messageId);
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(msg, Map.class);
        String orderId = map.get("orderId").toString();
        OrderBasicInfo orderInfo = orderBasicRepository.findByOrderId(orderId);
        if(orderInfo!=null){
            System.out.println("订单存在，无需补单");
           return;
        }
        OrderBasicInfo orderBasicInfo = new OrderBasicInfo();
        orderBasicRepository.save(orderBasicInfo);
        System.out.println("补单成功");
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
