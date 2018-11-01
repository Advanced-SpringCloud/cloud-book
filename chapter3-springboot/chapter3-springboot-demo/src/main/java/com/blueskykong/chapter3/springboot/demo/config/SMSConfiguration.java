package com.blueskykong.chapter3.springboot.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keets
 * @data 2018/3/20.
 */
@ConfigurationProperties(prefix = "sms")
@Configuration
@Data
public class SMSConfiguration {

    private int retryLimitationMinutes;

    private int validityMinute;

    private final List<String> types = new ArrayList<>();

}
