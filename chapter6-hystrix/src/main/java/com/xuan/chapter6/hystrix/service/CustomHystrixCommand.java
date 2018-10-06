package com.xuan.chapter6.hystrix.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.xuan.chapter.common.chapter5.dto.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created by xuan on 2018/3/14.
 */
public class CustomHystrixCommand extends HystrixCommand<Instance> {

    private static Logger logger = LoggerFactory.getLogger(CustomHystrixCommand.class);
    private RestTemplate restTemplate;
    private String serviceId;

    protected CustomHystrixCommand(RestTemplate restTemplate, String serviceId) {

        super(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup"));

        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
    }

    @Override
    protected Instance run() throws Exception {
        Instance instance = restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}", Instance.class, serviceId).getBody();
        return instance;
    }


    @Override
    protected Instance getFallback() {
        logger.info("Can not get Instance by serviceId {}", serviceId);
        return new Instance("error", "error", 0);
    }
}
