package com.blueskykong.client.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Chapter13ClientServiceaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13ClientServiceaApplication.class, args);
    }
}
