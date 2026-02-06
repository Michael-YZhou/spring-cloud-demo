package com.atguigu.order.service.impl;

import com.atguigu.order.bean.Order;
import com.atguigu.order.service.OrderService;
import com.atguigu.product.bean.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired // 一定要先导入 spring-cloud-starter-loadbalancer
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Order createOrder(Long productId, Long userId) {
        // 远程查询获取商品数据
        Product product = getProductFromRemoteWithLoadBalanceAnnotation(productId);

        Order order = new Order();

        order.setId(1L);
        // 计算订单总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("zhangsan");
        order.setAddress("尚硅谷");
        // 远程查询商品列表
        order.setProductList(Arrays.asList(product));

        return order;
    }

    private Product getProductFromRemote(Long productId) {
        // 1.获取到商品服务所在的所有机器IP+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);

        // 远程url
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        log.info("远程请求路径: {}", url);
        // 2.给远程发请求(会将json自动转成用来接收的class)
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    // 进阶2：完成负载均衡的发送请求
    private Product getProductFromRemoteWithLoadBalance(Long productId) {
        // 1.获取到商品服务所在的所有机器IP+port
        ServiceInstance instance = loadBalancerClient.choose("service-product");

        // 远程url
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        log.info("远程请求路径: {}", url);
        // 2.给远程发请求(会将json自动转成用来接收的class)
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    // 进阶3：完成负载均衡的发送请求
    private Product getProductFromRemoteWithLoadBalanceAnnotation(Long productId) {
        // 1.获取到商品服务所在的所有机器IP+port
        // ServiceInstance instance = loadBalancerClient.choose("service-product");

        // 远程url
        // String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/product/" + productId;
        // 用目标微服务名称代替url的 IP 和 Port
        String url = "http://service-product/product/" + productId;

        // 2.给远程发请求(service-product会在注入的restTemplate中通过注解被动态替换)
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}
