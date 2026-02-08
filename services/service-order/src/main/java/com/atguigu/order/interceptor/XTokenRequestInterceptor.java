package com.atguigu.order.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class XTokenRequestInterceptor implements RequestInterceptor {

    /**
     * 请求拦截器
     * @param template 请求模板包含了这次请求的所有信息，在方法体中可对其进行修改
     */
    @Override
    public void apply(RequestTemplate template) {
        System.out.println("启动请求拦截器 XTokenRequestInterceptor...");
        template.header("X-Token", UUID.randomUUID().toString());
    }
}
