package com.xuan.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
//会为项目自动配置必须的配置类，标识该服务为注册中心
@EnableEurekaServer
public class Chapter4EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter4EurekaServerApplication.class, args);
    }
}
