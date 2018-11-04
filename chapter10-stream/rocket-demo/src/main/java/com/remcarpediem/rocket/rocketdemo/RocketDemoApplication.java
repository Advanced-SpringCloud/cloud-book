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
     * error1:No route info of this topic 使用
     * 1 sh mqadmin clusterList -n localhost:9876
     * 2 sh mqadmin updateTopic -b 192.168.0.102:10911 -o 1 -t sms -n localhost:9876
     * https://rocketmq.apache.org/docs/simple-example/
     */
    public static void main(String[] args) {
        SpringApplication.run(RocketDemoApplication.class, args);
    }
}
