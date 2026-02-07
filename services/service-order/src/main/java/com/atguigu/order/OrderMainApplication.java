package com.atguigu.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // 启用Feign远程调用功能
public class OrderMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderMainApplication.class, args);
    }

    // 1. 项目启动就监听配置文件变化
    // 2. 发生变化后拿到变化值
    // 3. 发送邮件

    // 创建ApplicationRunner并放入项目容器中，容器启动时自动执行
    @Bean
    public ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        return (ApplicationArguments args) -> {
            nacosConfigManager.getConfigService().addListener(
                    "service-order.properties",
                    "DEFAULT_GROUP",
                    new Listener() {
                        // 这个方法用来获取监听器的任务要执行的线程池
                        @Override
                        public Executor getExecutor() {
                            // 监听器的任务要在线程池中执行
                            return Executors.newFixedThreadPool(4);
                        }

                        // 监听到变化后，会调用这个方法来拿到配置信息的变化值
                        @Override
                        public void receiveConfigInfo(String configInfo) {
                            System.out.println("变化的配置信息：" + configInfo);
                            System.out.println("邮件通知...");
                        }
                    }
            );
            System.out.println("OrderMainApplication.applicationRunner");
        };
    }
}
