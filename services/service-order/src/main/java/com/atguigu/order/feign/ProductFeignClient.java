package com.atguigu.order.feign;

import com.atguigu.order.feign.fallback.ProductFeignClientFallback;
import com.atguigu.product.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product", fallback = ProductFeignClientFallback.class) // 发送远程调用请求的feign客户端
public interface ProductFeignClient {

    //mvc注解的两套使用逻辑
    // 1. 标注在Controller上，是接受这样的请求
    // 2. 标注在feighClient上，是发送这样的请求

    // feign会自动连上注册中心，拿到所有指定服务的IP+port，然后负载均衡的发送请求
    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id); // 返回的json数据会自动映射为Product对象
}
