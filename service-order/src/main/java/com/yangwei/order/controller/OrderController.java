package com.yangwei.order.controller;

import com.yangwei.order.domain.OrderBasicInfo;
import com.yangwei.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨威
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 创建用户
     * @param orderBasicInfo 订单基本信息
     * @return ResponseEntity
     */
    @PostMapping("/createByOrderBasicInfo")
    public OrderBasicInfo createByOrderBasicInfo(OrderBasicInfo orderBasicInfo) {
        return orderService.createByOrderBasicInfo(orderBasicInfo);
    }

    /**
     * 查询用户
     * @param orderId 订单ID
     * @return ResponseEntity
     */
    @GetMapping("/findByOrderId")
    public OrderBasicInfo findByOrderId(String orderId){
        return orderService.findByOrderId(orderId);
    }
}
