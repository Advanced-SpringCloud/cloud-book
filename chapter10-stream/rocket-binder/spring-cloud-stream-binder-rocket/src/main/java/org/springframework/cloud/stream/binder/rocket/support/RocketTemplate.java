/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2018 All Rights Reserved.
 */
package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.core.ReplyToAddressCallback;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.rocket.properties.RocketProducerProperties;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author libing
 * @version $Id: RocketTemplate.java, v 0.1 2018年10月27日 上午10:38 zt Exp $
 */
public class RocketTemplate implements AmqpTemplate {

    protected RocketMQResourceManager                              resourceManager;
    protected ExtendedProducerProperties<RocketProducerProperties> producerProperties;
    protected DefaultMQProducer                                    producer;
    protected ObjectMapper                                         mapper;

    protected List<TopicConfig> topics;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected volatile boolean running = false;

    public RocketTemplate(RocketMQResourceManager resourceManager,
                          ExtendedProducerProperties<RocketProducerProperties> producerProperties, List<TopicConfig> topics) {
            this.resourceManager = resourceManager;
            this.producerProperties = producerProperties;
            this.mapper = new ObjectMapper();
            this.topics = topics;
    }

    public void start() throws MQClientException {
        String nameSrvConnectionString = this.resourceManager.getConfigurationProperties()
                .getNameSrvConnectionString();
        String groupName = producerProperties.getExtension().getGroupName();
        logger.info("=======================");
        for (TopicConfig topicConfig : topics) {
            logger.info(topicConfig.toString());
        }

        String topic = topics.get(0).getTopicName();
        if ("springCloudBus".equals(topic) && (StringUtils.isBlank(groupName))) {
            groupName = "springCloudBusGroup" + UUID.randomUUID().toString();
        }
        logger.info("[producer]nameSrvConnectionString:{},groupName:{}", nameSrvConnectionString, groupName);
        if (groupName != null) {
            this.producer = new DefaultMQProducer(groupName);
            this.producer.setNamesrvAddr(nameSrvConnectionString);
            this.producer.start();
//            try {
//                org.apache.rocketmq.common.message.Message msg = new org.apache.rocketmq.common.message.Message("sms" /* Topic */,
//                        "TagA" /* Tag */,
//                        ("Hello RocketMQ " + 1).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
//                );
//                SendResult result = this.producer.send(msg);
//                result.getMessageQueue();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public void stop() {
        if (this.producer != null) {
            this.producer.shutdown();
        }
    }


        /**
         * Getter method for property <tt>resourceManager</tt>.
         *
         * @return property value of resourceManager
         */
    public RocketMQResourceManager getResourceManager() {
        return resourceManager;
    }

    /**
     * Getter method for property <tt>producerProperties</tt>.
     *
     * @return property value of producerProperties
     */
    public ExtendedProducerProperties<RocketProducerProperties> getProducerProperties() {
        return producerProperties;
    }

    /**
     * Getter method for property <tt>producer</tt>.
     *
     * @return property value of producer
     */
    public DefaultMQProducer getProducer() {
        return producer;
    }

    /**
     * Getter method for property <tt>mapper</tt>.
     *
     * @return property value of mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * Getter method for property <tt>topics</tt>.
     *
     * @return property value of topics
     */
    public List<TopicConfig> getTopics() {
        return topics;
    }

    /**
     * Getter method for property <tt>logger</tt>.
     *
     * @return property value of logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Getter method for property <tt>running</tt>.
     *
     * @return property value of running
     */
    public boolean isRunning() {
        return running;
    }

    public SendResult sendRocketMQ(org.apache.rocketmq.common.message.Message message) throws AmqpException {
        SendResult result = null;
        try {
            result = producer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void send(Message message) throws AmqpException {

    }

    @Override
    public void send(String s, Message message) throws AmqpException {

    }

    @Override
    public void send(String s, String s1, Message message) throws AmqpException {

    }

    @Override
    public void convertAndSend(Object o) throws AmqpException {

    }

    @Override
    public void convertAndSend(String s, Object o) throws AmqpException {

    }

    @Override
    public void convertAndSend(String s, String s1, Object o) throws AmqpException {

    }

    @Override
    public void convertAndSend(Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {

    }

    @Override
    public void convertAndSend(String s, Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {

    }

    @Override
    public void convertAndSend(String s, String s1, Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {
        // AmqpOutboundEndpoint的handleRequestMessage方法会调用该方法
    }

    @Override
    public Message receive() throws AmqpException {
        return null;
    }

    @Override
    public Message receive(String s) throws AmqpException {
        return null;
    }

    @Override
    public Message receive(long l) throws AmqpException {
        return null;
    }

    @Override
    public Message receive(String s, long l) throws AmqpException {
        return null;
    }

    @Override
    public Object receiveAndConvert() throws AmqpException {
        return null;
    }

    @Override
    public Object receiveAndConvert(String s) throws AmqpException {
        return null;
    }

    @Override
    public Object receiveAndConvert(long l) throws AmqpException {
        return null;
    }

    @Override
    public Object receiveAndConvert(String s, long l) throws AmqpException {
        return null;
    }

    @Override
    public <T> T receiveAndConvert(ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T receiveAndConvert(String s, ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T receiveAndConvert(long l, ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T receiveAndConvert(String s, long l, ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> receiveAndReplyCallback) throws AmqpException {
        return false;
    }

    @Override
    public <R, S> boolean receiveAndReply(String s, ReceiveAndReplyCallback<R, S> receiveAndReplyCallback) throws AmqpException {
        return false;
    }

    @Override
    public <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> receiveAndReplyCallback, String s, String s1) throws AmqpException {
        return false;
    }

    @Override
    public <R, S> boolean receiveAndReply(String s, ReceiveAndReplyCallback<R, S> receiveAndReplyCallback, String s1, String s2)
            throws AmqpException {
        return false;
    }

    @Override
    public <R, S> boolean receiveAndReply(ReceiveAndReplyCallback<R, S> receiveAndReplyCallback,
                                          ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException {
        return false;
    }

    @Override
    public <R, S> boolean receiveAndReply(String s, ReceiveAndReplyCallback<R, S> receiveAndReplyCallback,
                                          ReplyToAddressCallback<S> replyToAddressCallback) throws AmqpException {
        return false;
    }

    @Override
    public Message sendAndReceive(Message message) throws AmqpException {
        return null;
    }

    @Override
    public Message sendAndReceive(String s, Message message) throws AmqpException {
        return null;
    }

    @Override
    public Message sendAndReceive(String s, String s1, Message message) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(Object o) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(String s, Object o) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(String s, String s1, Object o) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(String s, Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {
        return null;
    }

    @Override
    public Object convertSendAndReceive(String s, String s1, Object o, MessagePostProcessor messagePostProcessor) throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(Object o, ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(String s, Object o, ParameterizedTypeReference<T> parameterizedTypeReference)
            throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(String s, String s1, Object o, ParameterizedTypeReference<T> parameterizedTypeReference)
            throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(Object o, MessagePostProcessor messagePostProcessor,
                                             ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(String s, Object o, MessagePostProcessor messagePostProcessor,
                                             ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }

    @Override
    public <T> T convertSendAndReceiveAsType(String s, String s1, Object o, MessagePostProcessor messagePostProcessor,
                                             ParameterizedTypeReference<T> parameterizedTypeReference) throws AmqpException {
        return null;
    }
}