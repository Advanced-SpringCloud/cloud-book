package com.xuan.chapter4.eureka.client.service.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @create 2018-04-23 00:35
 **/
@RestController
public class SayHelloController {

    @RequestMapping(value = "/hello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "Hello, ".concat(name).concat("!");
    }

}
