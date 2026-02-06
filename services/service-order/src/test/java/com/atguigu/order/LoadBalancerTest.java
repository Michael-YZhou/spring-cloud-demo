package com.atguigu.order;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

//@SpringBootTest
public class LoadBalancerTest {

//    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Test
    void test() {
        ServiceInstance choose = loadBalancerClient.choose("service-provider");
        System.out.println("choose = " + choose.getHost() + ":" + choose.getPort());
    }
}
