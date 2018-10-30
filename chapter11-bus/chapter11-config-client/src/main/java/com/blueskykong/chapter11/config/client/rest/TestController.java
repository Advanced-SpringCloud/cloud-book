package com.blueskykong.chapter11.config.client.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by keets on 2018/1/7.
 */
@RestController
@RequestMapping("/cloud")
@RefreshScope
public class TestController {

    @Value("${cloud.version}")
    private String version;

    @GetMapping("/version")
    public String from() {
        return version;
    }
}
