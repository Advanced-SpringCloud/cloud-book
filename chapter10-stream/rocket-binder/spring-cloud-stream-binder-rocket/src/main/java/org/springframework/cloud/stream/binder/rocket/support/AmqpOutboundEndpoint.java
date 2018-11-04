/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2018 All Rights Reserved.
 */
package org.springframework.cloud.stream.binder.rocket.support;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.TopicConfig;
import org.apache.rocketmq.common.message.MessageConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.BinderHeaders;
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.binder.rocket.properties.RocketProducerProperties;
import org.springframework.integration.amqp.outbound.AbstractAmqpOutboundEndpoint;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.util.StringUtils;
import reactor.util.context.Context;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author libing
 * @version $Id: AmqpOutboundEndpoint.java, v 0.1 2018年10月27日 下午12:29 zt Exp $
 */
public class AmqpOutboundEndpoint extends AbstractAmqpOutboundEndpoint {

    protected RocketMQResourceManager                              resourceManager;
    protected     ExtendedProducerProperties<RocketProducerProperties> producerProperties;
    protected     ObjectMapper                                         mapper;

    protected List<TopicConfig> topics;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    protected volatile boolean running = false;

    private String charset = "UTF-8";

    private MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

    private RocketTemplate rocketTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public AmqpOutboundEndpoint(RocketTemplate template) {
        this.resourceManager = template.getResourceManager();
        this.producerProperties = template.getProducerProperties();
        this.mapper = new ObjectMapper();
        this.topics = template.getTopics();
        this.rocketTemplate = template;
    }


    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void start() {
        try {
            if (topics.size() == 0) {
                return;
            }
            rocketTemplate.start();
        } catch (Exception e) {
            logger.info("this.producer is not running: {} ", e.getMessage());
        }
        running = true;
    }

    @Override
    public void stop() {
        rocketTemplate.stop();
        running = false;
    }

    @Override
    protected Object handleRequestMessage(Message<?> message) {
            try {
                String topic = producerProperties.isPartitioned()
                        ? topics.get((Integer) message.getHeaders().get(BinderHeaders.PARTITION_HEADER)).getTopicName()
                        : topics.get(0).getTopicName();

                org.apache.rocketmq.common.message.Message rocketMessage =
                                    convertToRocketMsg(topic, message);
                logger.info("handleMessageInternal message:{}", rocketMessage.toString());
                SendResult sendResult = rocketTemplate.sendRocketMQ(rocketMessage);
                if (sendResult != null) {
                    logger.info(sendResult.toString());
                }
            } catch (Exception e) {
                logger.info("Exception: {}", e.getMessage());
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public Context currentContext() {
        return null;
    }

    protected RocketMQMessage convert(Message<?> message) throws Exception {
        String topic = producerProperties.isPartitioned()
                ? topics.get((Integer) message.getHeaders().get(BinderHeaders.PARTITION_HEADER)).getTopicName()
                : topics.get(0).getTopicName();

        logger.info("this.topics.size(): {}", this.topics.size());
        for (TopicConfig topicConfig : topics) {
            logger.info(topicConfig.toString());
        }
        byte[] payload = (byte[]) message.getPayload();
        byte[] rawPayloadNoHeaders = payload;

        MessageHeaders headers = message.getHeaders();

        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());

        }
//        try {
//            MessageValues mv = EmbeddedHeaderUtils.extractHeaders(payload);
//        } catch (Exception e) {
//            logger.info("Need to embed headers.");
//
//            MessageValues original = new MessageValues(payload, headers);
//
//            rawPayloadNoHeaders = EmbeddedHeaderUtils.embedHeaders(original, "contentType");
//        }

        try {
            String tags = this.producerProperties.getExtension().getTags();
            logger.info("topic:{}, tags:{}, rawPayloadNoHeaders:{}", topic, tags,
                    new String(rawPayloadNoHeaders, "UTF-8"));
            org.apache.rocketmq.common.message.Message msg = new org.apache.rocketmq.common.message.Message(topic, tags,
                    rawPayloadNoHeaders);
            RocketMQMessage pubSubMessage = new RocketMQMessage(msg);
            return pubSubMessage;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private org.apache.rocketmq.common.message.Message convertToRocketMsg(String destination, Message<?> message) {

        Object payloadObj = message.getPayload();
        byte[] payloads = (byte[]) message.getPayload();

        String[] tempArr = destination.split(":", 2);
        String topic = tempArr[0];
        String tags = "";
        if (tempArr.length > 1) {
            tags = tempArr[1];
        }

        org.apache.rocketmq.common.message.Message rocketMsg = new org.apache.rocketmq.common.message.Message(topic, tags, payloads);

        MessageHeaders headers = message.getHeaders();
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            Object keys = headers.get(MessageConst.PROPERTY_KEYS);
            if (!StringUtils.isEmpty(keys)) { // if headers has 'KEYS', set rocketMQ message key
                rocketMsg.setKeys(keys.toString());
            }

            // set rocketMQ message flag
            Object flagObj = headers.getOrDefault("FLAG", "0");
            int flag = 0;
            try {
                flag = Integer.parseInt(flagObj.toString());
            } catch (NumberFormatException e) {
                // ignore
            }
            rocketMsg.setFlag(flag);

            // set rocketMQ message waitStoreMsgOkObj
            Object waitStoreMsgOkObj = headers.getOrDefault("WAIT_STORE_MSG_OK", "true");
            boolean waitStoreMsgOK = Boolean.TRUE.equals(waitStoreMsgOkObj);
            rocketMsg.setWaitStoreMsgOK(waitStoreMsgOK);

            headers.entrySet().stream()
                    .filter(entry -> !Objects.equals(entry.getKey(), MessageConst.PROPERTY_KEYS)
                            && !Objects.equals(entry.getKey(), "FLAG")
                            && !Objects.equals(entry.getKey(), "WAIT_STORE_MSG_OK")) // exclude "KEYS", "FLAG", "WAIT_STORE_MSG_OK"
                    .forEach(entry -> {
                        rocketMsg.putUserProperty("USERS_" + entry.getKey(), String.valueOf(entry.getValue())); // add other properties with prefix "USERS_"
                    });

        }

        return rocketMsg;
    }

}