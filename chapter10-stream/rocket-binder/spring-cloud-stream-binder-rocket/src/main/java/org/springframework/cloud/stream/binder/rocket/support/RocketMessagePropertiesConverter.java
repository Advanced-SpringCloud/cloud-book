package org.springframework.cloud.stream.binder.rocket.support;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class RocketMessagePropertiesConverter {
    MessageProperties toMessageProperties(Map<String, String> headers, String charset) {
        MessageProperties target = new MessageProperties();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                String key = entry.getKey();
                if (MessageProperties.X_DELAY.equals(key)) {
                    Object value = entry.getValue();
                    if (value instanceof Integer) {
                        target.setReceivedDelay((Integer) value);
                    }
                }
                else {
                    target.setHeader(key, entry.getValue());
                }
            }
        }
        return new MessageProperties();
    }

    AMQP.BasicProperties fromMessageProperties(MessageProperties source, String charset) {
        return new AMQP.BasicProperties();
    }
}
