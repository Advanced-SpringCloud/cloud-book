package org.springframework.cloud.stream.binder.rocket.support;

import org.springframework.messaging.Message;

public interface Listener {
    void onMessage(Message message) throws Exception;
}
