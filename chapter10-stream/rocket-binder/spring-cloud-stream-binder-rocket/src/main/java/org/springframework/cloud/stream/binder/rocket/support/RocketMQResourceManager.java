package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.rocket.properties.RocketMQBinderConfigurationProperties;

public class RocketMQResourceManager {
    private RocketMQBinderConfigurationProperties configurationProperties;
    private ObjectMapper mapper;
    private Logger logger = LoggerFactory.getLogger(RocketMQResourceManager.class);

    public RocketMQResourceManager(RocketMQBinderConfigurationProperties configurationProperties) {
        this.configurationProperties = configurationProperties;
        this.mapper = new ObjectMapper();
    }

    public RocketMQBinderConfigurationProperties getConfigurationProperties() {
        return this.configurationProperties;
    }

}
