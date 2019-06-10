package com.yangwei.order.service;

import com.yangwei.order.domain.OrderBasicInfo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


/**
 * @author 杨威
 */
public interface OrderService extends RabbitTemplate.ConfirmCallback {

    /**
     * 根据订单Id查询订单
     * @param orderId 订单ID
     * @return OrderBasicInfo
     */
    OrderBasicInfo findByOrderId(String orderId);


    /**
     * 创建订单
     * @param orderBasicInfo 订单详细
     * @return ResponseEntity
     */
    OrderBasicInfo createByOrderBasicInfo(OrderBasicInfo orderBasicInfo);
}
