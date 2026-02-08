package com.atguigu.order.feign.fallback;

import com.atguigu.order.feign.ProductFeignClient;
import com.atguigu.product.bean.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductFeignClientFallback implements ProductFeignClient {
    @Override
    public Product getProductById(Long id) {
        System.out.println("兜底回调...");
        Product product = new Product();
        product.setId(0L);
        product.setPrice(new BigDecimal("0"));
        product.setProductName("");
        product.setNum(0);


        return null;
    }
}
