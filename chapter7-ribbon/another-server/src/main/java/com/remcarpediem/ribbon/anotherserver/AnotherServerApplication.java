package com.remcarpediem.ribbon.anotherserver;

import com.remcarpediem.ribbon.common.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class AnotherServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnotherServerApplication.class, args);
    }


}

