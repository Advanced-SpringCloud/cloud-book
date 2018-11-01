package com.xuan.chapter5.feign.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableEurekaClient
public class Chapter5FeignServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter5FeignServiceApplication.class, args);
    }
}
