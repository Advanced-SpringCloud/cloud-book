package com.xuan.chapter6.hystrix.controller;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.xuan.chapter.common.chapter5.dto.Instance;
import com.xuan.chapter6.hystrix.service.CustomCollapseCommand;
import com.xuan.chapter6.hystrix.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuan on 2018/3/9.
 */

@RestController
@RequestMapping("/instance")
public class InstanceController {


    private static final Logger logger = LoggerFactory.getLogger(InstanceController.class);


    @Autowired
    InstanceService instanceService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value = "rest-template/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdWithRestTemplate(@PathVariable("serviceId") String serviceId) {
        logger.info("Get Instance by serviceId {}", serviceId);
        return instanceService.getInstanceByServiceIdWithRestTemplate(serviceId);
    }

    @RequestMapping(value = "feign/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdWithFeign(@PathVariable("serviceId") String serviceId) {
        logger.info("Get Instance by serviceId {}", serviceId);
        return instanceService.getInstanceByServiceIdWithFeign(serviceId);
    }


    @RequestMapping(value = "async/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdAsync(@PathVariable("serviceId") String serviceId) throws ExecutionException, InterruptedException {
        logger.info("Get Instance by serviceId {}", serviceId);
        Instance instance = instanceService.getInstanceByServiceIdAsync(serviceId).get();
        return instance;
    }


    @RequestMapping(value = "observable/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdObservable(@PathVariable("serviceId") String serviceId) throws ExecutionException, InterruptedException {
        logger.info("Get Instance by serviceId {}", serviceId);
        Instance instance = instanceService.getInstanceByServiceIdObservable(serviceId).toBlocking().single();
        return instance;
    }

    @RequestMapping(value = "custom/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceIdCustom(@PathVariable("serviceId") String serviceId) {
        Instance instance = instanceService.getInstanceByServiceIdCustom(serviceId);
        return instance;
    }

    @RequestMapping(value = "batch/test1", method = RequestMethod.GET)
    public Instance getInstancesBatch1() throws ExecutionException, InterruptedException {

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<Instance> future1 = instanceService.getInstanceByServiceId("test1");
        Future<Instance> future2 = instanceService.getInstanceByServiceId("test2");
        Future<Instance> future3 = instanceService.getInstanceByServiceId("test3");
        future1.get();
        future2.get();
        future3.get();
        TimeUnit.MILLISECONDS.sleep(1000);
        Future<Instance> future4 = instanceService.getInstanceByServiceId("test4");
        Instance instance = future4.get();
        context.close();
        return instance;
    }


    @RequestMapping(value = "batch/test2", method = RequestMethod.GET)
    public Instance getInstancesBatch2() throws ExecutionException, InterruptedException {

        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        CustomCollapseCommand c1 = new CustomCollapseCommand("test1");
        CustomCollapseCommand c2 = new CustomCollapseCommand("test2");
        CustomCollapseCommand c3 = new CustomCollapseCommand("test3");
        CustomCollapseCommand c4 = new CustomCollapseCommand("test4");

        Future<Instance> future1 = c1.queue();
        Future<Instance> future2 = c2.queue();
        Future<Instance> future3 = c3.queue();

        future1.get();
        future2.get();
        future3.get();
        TimeUnit.MILLISECONDS.sleep(1000);
        Future<Instance> future4 = c4.queue();
        Instance instance = future4.get();
        context.close();
        return instance;
    }

}
