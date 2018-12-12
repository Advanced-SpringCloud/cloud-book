package com.remcarpediem.ribbon.server;

import com.remcarpediem.ribbon.common.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @RequestMapping("/order")
    public Order getOrderDetail() {
        Order order = new Order();
        order.setId(1L);
        order.setContent("from server");
        return order;
    }
}

