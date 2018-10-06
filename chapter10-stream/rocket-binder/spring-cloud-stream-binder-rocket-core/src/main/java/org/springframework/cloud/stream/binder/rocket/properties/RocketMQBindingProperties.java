package org.springframework.cloud.stream.binder.rocket.properties;

public class RocketMQBindingProperties {

    private RocketConsumerProperties consumer = new RocketConsumerProperties();

    private RocketProducerProperties producer = new RocketProducerProperties();

    public RocketConsumerProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(RocketConsumerProperties consumer) {
        this.consumer = consumer;
    }

    public RocketProducerProperties getProducer() {
        return producer;
    }

    public void setProducer(RocketProducerProperties producer) {
        this.producer = producer;
    }
}

