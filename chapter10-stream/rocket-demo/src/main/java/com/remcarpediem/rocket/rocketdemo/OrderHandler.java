package com.remcarpediem.rocket.rocketdemo;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@EnableBinding(Sink.class)
@Component
public class OrderHandler {
    @StreamListener(Sink.INPUT)
    public void handle(Order order) {
        Long id = order.getId();
        String content = order.getContent();
    }
}
