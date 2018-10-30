package com.blueskykong.client.servicea.rest;

import com.blueskykong.client.servicea.feign.ServiceBClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author keets
 * @data 2018/4/10.
 */
@RestController
@RequestMapping("/api")
public class ServiceAController {


    @Autowired
    ServiceBClient serviceBClient;

    @GetMapping("/call-a")
    public String fromServiceA() {
        return "from serviceA";

    }

    @GetMapping("/service-b")
    public String fromServiceB() {
        return serviceBClient.fromB();

    }


}
