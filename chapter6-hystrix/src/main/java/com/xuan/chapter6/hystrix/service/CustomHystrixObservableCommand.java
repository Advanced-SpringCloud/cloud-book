package com.xuan.chapter6.hystrix.service;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixObservableCommand;
import com.xuan.chapter.common.chapter5.dto.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import rx.Observable;

/**
 * Created by xuan on 2018/3/19.
 */
public class CustomHystrixObservableCommand extends HystrixObservableCommand<Instance> {


    private static Logger logger = LoggerFactory.getLogger(CustomHystrixObservableCommand.class);
    private RestTemplate restTemplate;
    private String serviceId;

    protected CustomHystrixObservableCommand(RestTemplate restTemplate, String serviceId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup")));

//        super(HystrixCommandGroupKey.Factory.asKey("CustomServiceGroup"));
        this.restTemplate = restTemplate;
        this.serviceId = serviceId;
    }

    @Override
    protected Observable<Instance> construct() {
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(restTemplate.getForEntity("http://FEIGN-SERVICE/feign-service/instance/{serviceId}", Instance.class, serviceId).getBody());
                        subscriber.onCompleted();
                    }

                }

        );
    }

    @Override
    protected Observable<Instance> resumeWithFallback() {
        logger.info("Can not get Instance by serviceId {}", serviceId);
        return Observable.create(
                subscriber -> {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(new Instance("error", "error", 0));
                        subscriber.onCompleted();
                    }

                }

        );
    }


}
