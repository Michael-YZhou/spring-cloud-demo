package com.atguigu.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "order") // 配置批量绑定在nacos下，可以无需@RefreshScope就能实现自动刷新
@Data
public class OrderProperties {

    private String timeout;
    private String autoConfirm; // nacos中属性的短横线自动映射为驼峰命名
}
