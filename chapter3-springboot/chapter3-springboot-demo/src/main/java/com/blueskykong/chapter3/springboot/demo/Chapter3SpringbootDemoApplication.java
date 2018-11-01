package com.blueskykong.chapter3.springboot.demo;

import com.blueskykong.chapter3.springboot.demo.config.SMSConfiguration;
import com.xuan.chapter3.logfilter.starter.annotation.EnableLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@Slf4j
@EnableLogFilter
public class Chapter3SpringbootDemoApplication {

    @Autowired
    private SMSConfiguration smsConfiguration;


    public static void main(String[] args) {
        SpringApplication.run(Chapter3SpringbootDemoApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        log.info("smsConfiguration is : {}", smsConfiguration);
        return "this is a demo boot.";
    }
}
