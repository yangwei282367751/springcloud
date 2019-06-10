package com.yangwei.goods.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.yangwei.common.utils.StringUtil;
import com.yangwei.goods.service.GoodsService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author 杨威
 */
@Component
public class GoodsConsumer {
    @Resource
    private GoodsService goodsService;

    @RabbitListener(queues = "order_dic_queue")
    public void process(Message message,  Channel channel)throws Exception{
        String messageId = message.getMessageProperties().getMessageId();
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("修改库存" + msg + ",消息id:" + messageId);
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(msg, Map.class);
        String orderId = map.get("orderId").toString();
        System.out.println("orderId:" + orderId);
        if(StringUtil.isEmpty(orderId)){
            return;
        }
        goodsService.updateStocksNumberByGoodsId("402848e46b0b9991016b0b99f8c70000", 10L);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
