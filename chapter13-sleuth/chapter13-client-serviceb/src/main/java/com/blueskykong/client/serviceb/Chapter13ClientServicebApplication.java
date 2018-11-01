package com.blueskykong.client.serviceb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Chapter13ClientServicebApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13ClientServicebApplication.class, args);
    }
}
