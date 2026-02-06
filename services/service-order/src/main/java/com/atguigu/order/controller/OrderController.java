package com.atguigu.order.controller;

import com.atguigu.order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope // Nacos自动刷新配置
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @Value("${order.timeout}")
//    private String orderTimeout;
//    @Value("${order.auto-confirm}")
//    private String orderAutoConfirm;

    @Autowired
    private OrderProperties orderProperties;

    /**
     * 获取订单配置信息
     */
    @GetMapping("/config")
    public String config() {
        return "order.timeout: " + orderProperties.getTimeout() + " order.auto-confirm: " + orderProperties.getAutoConfirm();
    }

    /**
     * 创建订单
     */
    @GetMapping("/create")
    public Order createOrder(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(productId, userId);
        return order;
    }
}
