package com.remcarpediem.rocket.rocketdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableBinding(Source.class)
public class TestController {

    @Autowired
    private Source source;

    @PostMapping("/test")
    public void sendOrder(@RequestBody Order order) {
        source.output().send(MessageBuilder.withPayload(order).setHeader(MessageHeaders.CONTENT_TYPE, "application/json").build());
    }
}
