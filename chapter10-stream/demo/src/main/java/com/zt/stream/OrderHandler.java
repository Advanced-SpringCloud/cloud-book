package com.zt.stream;

import com.zt.stream.beans.Order;
import com.zt.stream.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class
OrderHandler {
    @Autowired
    OrderService orderService;
    @StreamListener(Sink.INPUT)
    public void handle(Order order) {
        orderService.handle(order);
    }

}
