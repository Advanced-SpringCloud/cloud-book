package com.remcarpediem.ribbon.anotherserver;

import com.remcarpediem.ribbon.common.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/order")
    public Order getOrderDetail() {
        Order order = new Order();
        order.setId(2L);
        order.setContent("from another server");
        return order;
    }
}
