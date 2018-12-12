package com.remcarpediem.ribbon.client;

import com.remcarpediem.ribbon.common.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RibbonClient(value = "trade", configuration = RibbonConfiguration.class)
public class TestController {
    @Autowired
    RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/test")
    public String test() {
        Order order = this.restTemplate.getForObject("http://trade/order", Order.class);
        return String.format("the order id is %s, content is %s!", order.getId(), order.getContent());
    }
}
