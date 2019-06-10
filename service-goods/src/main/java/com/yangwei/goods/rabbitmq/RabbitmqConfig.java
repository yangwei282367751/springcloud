package com.yangwei.goods.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ配置
 * @author  Wilson Yang
 * @date 2019-06-01
 */
@Component
public class RabbitmqConfig {

    /**
     * 下单并且修改库存存队列
     */
    public static final String ORDER_DIC_QUEUE = "order_dic_queue";

    /**
     * 下单并且修改库存交换机
     */
    private static final String ORDER_EXCHANGE = "order_exchange";

    /**
     * 定义订单队列
     */
    @Bean
    public Queue directOrderDicQueue(){
      return new Queue(ORDER_DIC_QUEUE);
    }

    /**
     * 定义交换机队列
     */
    @Bean
    DirectExchange directOrderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    /**
     * 订单队列与交换机绑定
     */
    @Bean
    Binding bindingExchangeOrderDicQueue(){
        return BindingBuilder.bind(directOrderDicQueue()).to(directOrderExchange()).with("orderRoutingKey");
    }

}
