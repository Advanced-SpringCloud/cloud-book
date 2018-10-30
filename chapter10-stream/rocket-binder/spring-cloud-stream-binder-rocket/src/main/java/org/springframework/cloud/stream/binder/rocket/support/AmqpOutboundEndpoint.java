/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2018 All Rights Reserved.
 */
package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.TopicConfig;
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
import reactor.util.context.Context;

import java.util.List;
import java.util.Map;

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

    private RocketTemplate rocketTemplate;

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
                RocketMQMessage pubSubMessage = convert(message);
                logger.info("handleMessageInternal message:{}", pubSubMessage.getMessage().toString());
                SendResult sendResult = rocketTemplate.sendRocketMQ(pubSubMessage.getMessage());
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
        // String encodedHeaders = encodeHeaders(message.getHeaders());
        String topic = producerProperties.isPartitioned()
                ? topics.get((Integer) message.getHeaders().get(BinderHeaders.PARTITION_HEADER)).getTopicName()
                : topics.get(0).getTopicName();

        // System.out.println(message.getPayload() instanceof byte[]);
        logger.info("this.topics.size(): {}", this.topics.size());
        for (TopicConfig topicConfig : topics) {
            logger.info(topicConfig.toString());
        }
        byte[] payload = (byte[]) message.getPayload();
        byte[] rawPayloadNoHeaders = payload;

        MessageHeaders headers = message.getHeaders();

        // Object contentType = headers.get("contentType");
        // if(contentType != null) {
        // Class<?> clazz =
        // MessageConverterUtils.getJavaTypeForJavaObjectContentType(MimeType.valueOf(contentType.toString()));
        // Input input = new Input(new ByteArrayInputStream(byteArray));
        // Bar readObject = kryo.readObject(input, Bar.class);
        //
        // }

        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println("key : " + entry.getKey() + " value : " + entry.getValue());
        }
        try {
            MessageValues mv = EmbeddedHeaderUtils.extractHeaders(payload);
        } catch (Exception e) {
            // e.printStackTrace();
            logger.info("Need to embed headers.");

            MessageValues original = new MessageValues(payload, headers);
            // String[] headersArray = new String[headers.keySet().size()];
            // int i = 0;
            // for (Iterator<String> iterator = headers.keySet().iterator();
            // iterator.hasNext();) {
            // String s = iterator.next();
            // headersArray[i++] = s;
            // }

            rawPayloadNoHeaders = EmbeddedHeaderUtils.embedHeaders(original, "contentType");
        }

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

}