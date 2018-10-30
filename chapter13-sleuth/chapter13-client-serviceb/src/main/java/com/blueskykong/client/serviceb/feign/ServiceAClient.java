package com.blueskykong.client.serviceb.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author keets
 * @data 2018/4/10.
 */

@FeignClient("service-a")
public interface ServiceAClient {

    @RequestMapping(value = "/api/call-a")
    public String fromA();

}
