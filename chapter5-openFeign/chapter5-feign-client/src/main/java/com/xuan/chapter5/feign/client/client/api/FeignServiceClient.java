package com.xuan.chapter5.feign.client.client.api;

import com.xuan.chapter.common.chapter5.dto.Instance;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xuan on 2018/2/3.
 */
@FeignClient("feign-service")
public interface FeignServiceClient {


    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId);

    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.DELETE)
    public String deleteInstanceByServiceId(@PathVariable("serviceId") String serviceId);

    @RequestMapping(value = "/instance", method = RequestMethod.POST)
    public String createInstance(@RequestBody Instance instance);

    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.PUT)
    public String updateInstanceByServiceId(@RequestBody Instance instance, @PathVariable("serviceId") String serviceId);
}
