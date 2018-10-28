package org.springframework.cloud.stream.binder.rocket.support;

import org.springframework.messaging.Message;

public class RocketDelivery {

    private final Message messageExt;


    public RocketDelivery(Message messageExt) { //NOSONAR
        this.messageExt = messageExt;
    }

    /**
     * Retrieve the message envelope.
     * @return the message envelope.
     */
    public Message getMessage() {
        return this.messageExt;
    }

}

