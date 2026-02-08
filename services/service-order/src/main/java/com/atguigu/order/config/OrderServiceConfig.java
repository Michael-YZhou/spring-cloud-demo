package com.atguigu.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderServiceConfig {

//    @Bean
    Retryer retryer() {
        return new Retryer.Default();
    }

    // 配置feign日志级别
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @LoadBalanced // 注解式负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
