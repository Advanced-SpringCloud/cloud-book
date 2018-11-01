/**
 * Superid.menkor.com Inc.
 * Copyright (c) 2012-2018 All Rights Reserved.
 */
package org.springframework.cloud.stream.binder.rocket.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.exception.FatalListenerStartupException;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.rocket.properties.RocketConsumerProperties;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 *
 * @author libing
 * @version $Id: SimpleRocketMessageListenerContainer.java, v 0.1 2018年10月27日 下午4:04 zt Exp $
 */
public class SimpleRocketMessageListenerContainer extends
        AbstractMessageListenerContainer {
    private AsyncMessageProcessingConsumer asyncMessageProcessingConsumer;
    private Executor executor;
    public static final long DEFAULT_RECEIVE_TIMEOUT = 1000;
    private List<RocketBlockingQueueConsumer> consumers = new ArrayList<>();
    protected final Object consumersMonitor = new Object(); //NOSONAR
    private long receiveTimeout = DEFAULT_RECEIVE_TIMEOUT;
    private int txSize = 1;
    private RocketMQResourceManager                              resourceManager;
    private String                                               topic;
    private String                                               consumerGroup;
    private ExtendedConsumerProperties<RocketConsumerProperties> extendedConsumerProperties;

    public SimpleRocketMessageListenerContainer() {
        executor = Executors.newFixedThreadPool(10);
    }


    @Override
    public void start() {
        try {
            doStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
        synchronized (this.consumersMonitor) {
            //初始化Consumer
            int newConsumers = initializeConsumers();
            Set<AsyncMessageProcessingConsumer>
                    processors = new HashSet<>();
            //对于每个RocketBlockingQueueConsumer启动一个
            //AsyncMessageProcessingConsumer来执行任务
            for (RocketBlockingQueueConsumer consumer : this.consumers) {
                SimpleRocketMessageListenerContainer.
                        AsyncMessageProcessingConsumer processor = new
                        SimpleRocketMessageListenerContainer.
                                AsyncMessageProcessingConsumer(consumer);
                processors.add(processor);
                getTaskExecutor().execute(processor);
            }
        }
    }

    @Override
    protected void doStart() throws Exception {
        // do nothing
        super.doStart();
    }

    @Override
    protected void doInitialize() throws Exception {
    }

    @Override
    protected void doShutdown() {
    }

    private int initializeConsumers() {
        RocketBlockingQueueConsumer rocketBlockingQueueConsumer = new RocketBlockingQueueConsumer(1);
        rocketBlockingQueueConsumer.setConsumerGroup(consumerGroup);
        rocketBlockingQueueConsumer.setExtendedConsumerProperties(extendedConsumerProperties);
        rocketBlockingQueueConsumer.setResourceManager(resourceManager);
        rocketBlockingQueueConsumer.setTopic(topic);
        consumers.add(rocketBlockingQueueConsumer);
        return 1;
    }

    class AsyncMessageProcessingConsumer implements Runnable {

        private final RocketBlockingQueueConsumer consumer;

        private final CountDownLatch start;

        private volatile FatalListenerStartupException startupException;

        AsyncMessageProcessingConsumer(RocketBlockingQueueConsumer consumer) {
            this.consumer = consumer;
            this.start = new CountDownLatch(1);
        }

        @Override
        public void run() {

            if (!isActive()) {
                return;
            }

            this.consumer.start();

            try {
                //只要consumer的状态正常，就会一直循环
                while (isActive(this.consumer) || this.consumer.hasDelivery() || !this.consumer.cancelled()) {
                    boolean receivedOk = doReceiveAndExecute(this.consumer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }


    private boolean doReceiveAndExecute(RocketBlockingQueueConsumer consumer) throws Throwable { //NOSONAR

        for (int i = 0; i < this.txSize; i++) {

            logger.trace("Waiting for message from consumer.");
            Message message = null;
            try {
                message = consumer.nextMessage(this.receiveTimeout);
            } catch (Exception e) {
//                e.printStackTrace();
            }
            if (message == null) {
                break;
            }
            try {
                executeListener(message);
            }
            catch (ImmediateAcknowledgeAmqpException e) {
                break;
            }
            catch (Throwable ex) { //NOSONAR

            }
        }
        return true;
    }

    private void executeListener(Message message) throws Exception{
        Object listener = this.getMessageListener();
        if (listener instanceof Listener) {
            Listener listenerImpl = (Listener)listener;
            listenerImpl.onMessage(message);
        }
    }

    private boolean isActive(RocketBlockingQueueConsumer consumer) {
        boolean consumerActive;
        synchronized (this.consumersMonitor) {
            consumerActive = this.consumers != null && this.consumers.contains(consumer);
        }
        return consumerActive && this.isActive();
    }


    public void setAsyncMessageProcessingConsumer(AsyncMessageProcessingConsumer asyncMessageProcessingConsumer) {
        this.asyncMessageProcessingConsumer = asyncMessageProcessingConsumer;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public void setConsumers(List<RocketBlockingQueueConsumer> consumers) {
        this.consumers = consumers;
    }

    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }

    public void setTxSize(int txSize) {
        this.txSize = txSize;
    }

    public void setResourceManager(RocketMQResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public void
    setTopic(String topic) {
        this.topic = topic;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setExtendedConsumerProperties(ExtendedConsumerProperties<RocketConsumerProperties> extendedConsumerProperties) {
        this.extendedConsumerProperties = extendedConsumerProperties;
    }
}