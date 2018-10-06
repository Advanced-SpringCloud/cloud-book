package com.xuan.chapter5.feign.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Chapter5FeignClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(Chapter5FeignClientApplication.class, args);
    }
}
