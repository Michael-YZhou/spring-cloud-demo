package com.atguigu.order.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.atguigu.order.bean.Order;
import com.atguigu.order.properties.OrderProperties;
import com.atguigu.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope // Nacos自动刷新配置
//@RequestMapping("/api/order")
@Slf4j
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
        return "order.timeout: " + orderProperties.getTimeout() +
                " order.auto-confirm: " + orderProperties.getAutoConfirm() +
                " order.db-url: " + orderProperties.getDbUrl();
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

    /**
     * 创建秒杀订单
     */
    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order", fallback = "seckillFallback")
    public Order seckill(@RequestParam("userId") Long userId,
                             @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(productId, userId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    public Order seckillFallback(Long userId, Long productId, Throwable exception) {
        Order order = new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress("秒杀失败,异常信息： " + exception.getClass());
        return order;
    }

    /**
     * 模拟写数据库
     */
    @GetMapping("/writeDb")
    public String writeDb() {
        return "writeDb success...";
    }

    /**
     * 模拟读数据库
     */
    @GetMapping("/readDb")
    public String readDb() {
        log.info("readDb...");
        return "readDb success...";
    }
}
