/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2018 All Rights Reserved.
 */
package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.binder.rocket.properties.RocketConsumerProperties;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.messaging.Message;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author libing
 * @version $Id: RocketInboundChannelAdapter.java, v 0.1 2018年10月27日 下午4:05 zt Exp $
 */
public class RocketInboundChannelAdapter extends MessageProducerSupport {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    private SimpleRocketMessageListenerContainer listenerContainer;

    public RocketInboundChannelAdapter(SimpleRocketMessageListenerContainer container) {
        this.listenerContainer = container;
        this.listenerContainer.setMessageBuilderFactory(getMessageBuilderFactory());
    }

    @Override
    protected void onInit() {
        super.onInit();
        Listener messageListener = new ListenerImpl();
        this.listenerContainer.setMessageListener(messageListener);
    }

    @Override
    protected void doStart() {
        listenerContainer.start();
    }

    @Override
    protected void doStop() {
    }

    public class ListenerImpl implements Listener, MessageListener {
        @Override
        public void onMessage(Message message) throws Exception {
            try {
                this.createAndSend(message);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onMessage(org.springframework.amqp.core.Message message) {

        }

        private void createAndSend(Message message) {
            RocketInboundChannelAdapter.this.sendMessage(message);
        }
    }
}