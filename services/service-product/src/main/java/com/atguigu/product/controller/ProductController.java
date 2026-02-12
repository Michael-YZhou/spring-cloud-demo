package com.atguigu.product.controller;

import com.atguigu.product.bean.Product;
import com.atguigu.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@RequestMapping("/api/product")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 查询商品
     */
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") Long productId,
                              HttpServletRequest request) {
        String header = request.getHeader("X-Token");
        System.out.println("X-Token: " + header);
        System.out.println("商品服务收到订单请求");
        Product product = productService.getProductById(productId);

        return product;
    }
}
