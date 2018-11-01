package org.springframework.cloud.stream.binder.rocket.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.ExtendedBindingProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@ConfigurationProperties("spring.cloud.stream.rocketmq")
public class
RocketMQExtendedBindingProperties
        implements ExtendedBindingProperties<RocketConsumerProperties, RocketProducerProperties> {

    private Map<String, RocketMQBindingProperties> bindings = new HashMap<>();

    public Map<String, RocketMQBindingProperties> getBindings() {
        return this.bindings;
    }

    public void setBindings(Map<String, RocketMQBindingProperties> bindings) {
        this.bindings = bindings;
    }

    @Override
    public RocketConsumerProperties getExtendedConsumerProperties(String channelName) {
        if (this.bindings.containsKey(channelName) && this.bindings.get(channelName).getConsumer() != null) {
            return this.bindings.get(channelName).getConsumer();
        } else {
            return new RocketConsumerProperties();
        }
    }

    @Override
    public RocketProducerProperties getExtendedProducerProperties(String channelName) {
        if (this.bindings.containsKey(channelName) && this.bindings.get(channelName).getProducer() != null) {
            return this.bindings.get(channelName).getProducer();
        } else {
            return new RocketProducerProperties();
        }
    }
}

