package com.xuan.chapter6.hystrix.service;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xuan.chapter.common.chapter5.dto.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author
 * @create 2018-03-19 23:19
 **/
public class CustomCollapseCommand extends HystrixCollapser<List<Instance>, Instance, String> {


    public String serviceId;


    public CustomCollapseCommand(String serviceId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("customCollapseCommand")));
        this.serviceId = serviceId;
    }


    @Override
    public String getRequestArgument() {
        return serviceId;
    }

    @Override
    protected HystrixCommand<List<Instance>> createCommand(Collection<CollapsedRequest<Instance, String>> collapsedRequests) {
        List<String> ids = collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList());
        return new InstanceBatchCommand(ids);
    }

    @Override
    protected void mapResponseToRequests(List<Instance> batchResponse, Collection<CollapsedRequest<Instance, String>> collapsedRequests) {

        int count = 0;
        for (CollapsedRequest<Instance, String> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }

    }


    private static final class InstanceBatchCommand extends HystrixCommand<List<Instance>> {

        private List<String> serviceIds;
        private static String DEFAULT_SERVICE_ID = "application";
        private static String DEFAULT_HOST = "localhost";
        private static int DEFAULT_PORT = 8080;
        private static Logger logger = LoggerFactory.getLogger(InstanceBatchCommand.class);


        protected InstanceBatchCommand(List<String> serviceIds) {
            super(HystrixCommandGroupKey.Factory.asKey("instanceBatchGroup"));
            this.serviceIds = serviceIds;
        }

        @Override
        protected List<Instance> run() throws Exception {
            List<Instance> instances = new ArrayList<>();
            logger.info("start batch!");
            for (String s : serviceIds) {
                instances.add(new Instance(s, DEFAULT_HOST, DEFAULT_PORT));
            }
            return instances;
        }
    }
}


