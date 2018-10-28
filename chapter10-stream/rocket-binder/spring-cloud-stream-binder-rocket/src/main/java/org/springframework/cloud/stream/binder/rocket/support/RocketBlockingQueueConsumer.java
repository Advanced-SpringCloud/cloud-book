package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
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
import org.springframework.cloud.stream.binder.EmbeddedHeaderUtils;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.MessageValues;
import org.springframework.cloud.stream.binder.rocket.properties.RocketConsumerProperties;
import org.springframework.integration.support.DefaultMessageBuilderFactory;
import org.springframework.integration.support.MessageBuilderFactory;
import org.springframework.messaging.Message;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RocketBlockingQueueConsumer {
    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    private ObjectMapper mapper;
    private RocketMQResourceManager resourceManager;
    private String topic;
    private String consumerGroup;
    private ExtendedConsumerProperties<RocketConsumerProperties> extendedConsumerProperties;
    private DefaultMQPushConsumer consumer;
    private MessageBuilderFactory messageBuilderFactory;

    private final BlockingQueue<RocketDelivery> queue;
    private volatile ShutdownSignalException shutdown;
    private RocketMessagePropertiesConverter messagePropertiesConverter;


    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setResourceManager(RocketMQResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setExtendedConsumerProperties(ExtendedConsumerProperties<RocketConsumerProperties> extendedConsumerProperties) {
        this.extendedConsumerProperties = extendedConsumerProperties;
    }

    public void setConsumer(DefaultMQPushConsumer consumer) {
        this.consumer = consumer;
    }

    public void setMessageBuilderFactory(MessageBuilderFactory messageBuilderFactory) {
        this.messageBuilderFactory = messageBuilderFactory;
    }

    public void setShutdown(ShutdownSignalException shutdown) {
        this.shutdown = shutdown;
    }

    public void setMessagePropertiesConverter(RocketMessagePropertiesConverter messagePropertiesConverter) {
        this.messagePropertiesConverter = messagePropertiesConverter;
    }

    public RocketBlockingQueueConsumer(int prefetchCount) {
        super();
        this.queue = new LinkedBlockingQueue<RocketDelivery>(prefetchCount);
        messagePropertiesConverter = new RocketMessagePropertiesConverter();
        this.messageBuilderFactory = new DefaultMessageBuilderFactory();
    }

    public void start() {
        String nameSrvConnectionString = this.resourceManager.getConfigurationProperties().getNameSrvConnectionString();
        String tags = this.extendedConsumerProperties.getExtension().getTags();
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(nameSrvConnectionString);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setMessageModel(MessageModel.BROADCASTING);
        try {
            consumer.subscribe(this.topic, tags);
            consumer.registerMessageListener(new InnerConsumer());
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public boolean hasDelivery() {
        return true;
    }

    public boolean cancelled() {
        return true;
    }

    public Message nextMessage(long timeout) throws InterruptedException, ShutdownSignalException {
        Message message = handle(this.queue.poll(timeout, TimeUnit.MILLISECONDS));
        if (message == null ) {
            throw new ConsumerCancelledException();
        }
        return message;
    }

    private Message handle(RocketDelivery delivery) throws InterruptedException {
        if ((delivery == null && this.shutdown != null)) {
            throw this.shutdown;
        }
        if (delivery == null) {
            return null;
        }
        return delivery.getMessage();
    }

    private final class InnerConsumer implements MessageListenerConcurrently {

        public InnerConsumer() {
            super();
        }

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            for (MessageExt messageExt : list) {
                byte[] payload = messageExt.getBody();
                try {
                    logger.info("Listener:" + new String(payload, "UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                try {
                    MessageValues mv = EmbeddedHeaderUtils.extractHeaders(payload);
                    logger.info("mv.payload:{}, headers:{}", mv.getPayload(), mv.getHeaders());
                    org.springframework.messaging.Message<?> internalMsgObject = messageBuilderFactory.withPayload((byte[]) mv.getPayload())
                            .copyHeaders(mv.getHeaders()).build();
                    RocketBlockingQueueConsumer.this.queue.put(new RocketDelivery(internalMsgObject));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("==========" + e.getMessage());
                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }


}
