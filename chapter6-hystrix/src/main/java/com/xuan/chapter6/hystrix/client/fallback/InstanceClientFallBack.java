package com.xuan.chapter6.hystrix.client.fallback;

import com.xuan.chapter.common.chapter5.dto.Instance;
import com.xuan.chapter6.hystrix.client.api.InstanceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by xuan on 2018/3/9.
 */

@Component
public class InstanceClientFallBack implements InstanceClient {

    private static Logger logger = LoggerFactory.getLogger(InstanceClientFallBack.class);

    @Override
    public Instance getInstanceByServiceId(String serviceId) {
        logger.info("Can not get Instance by serviceId {}", serviceId);
        return new Instance("error", "error", 0);
    }
}
