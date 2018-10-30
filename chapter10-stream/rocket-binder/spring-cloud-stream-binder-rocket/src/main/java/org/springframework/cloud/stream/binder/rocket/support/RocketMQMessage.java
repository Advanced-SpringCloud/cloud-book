package org.springframework.cloud.stream.binder.rocket.support;

import org.apache.rocketmq.common.message.Message;

public class RocketMQMessage {
    private final Message message;

    public RocketMQMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
