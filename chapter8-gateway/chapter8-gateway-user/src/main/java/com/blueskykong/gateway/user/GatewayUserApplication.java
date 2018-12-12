package com.blueskykong.gateway.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@RestController
public class GatewayUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayUserApplication.class, args);
    }

    ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>();

    Log log = LogFactory.getLog(getClass());

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

    @GetMapping("/exception")
    public String testException(@RequestParam("key") String key, @RequestParam(name = "count", defaultValue = "3") int count) {
        AtomicInteger num = map.computeIfAbsent(key, s -> new AtomicInteger());
        int i = num.incrementAndGet();
        log.warn("Retry count: " + i);
        if (i < count) {
            throw new IllegalArgumentException("temporarily broken");
        }
        return String.valueOf(i);
    }

    @PostMapping("/body")
    public String testException() {
        String content = "ok";
        System.out.println(content);
        return content;
    }
}
