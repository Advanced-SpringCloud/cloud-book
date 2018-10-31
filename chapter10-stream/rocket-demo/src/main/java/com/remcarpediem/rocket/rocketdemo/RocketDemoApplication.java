package com.remcarpediem.rocket.rocketdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("application.yml")
@EnableConfigurationProperties
public class RocketDemoApplication {
    /**
     * error1:No route info of this topic https://blog.csdn.net/yangding_/article/details/62419383
     * https://rocketmq.apache.org/docs/simple-example/
     */
    public static void main(String[] args) {
        SpringApplication.run(RocketDemoApplication.class, args);
    }
}
