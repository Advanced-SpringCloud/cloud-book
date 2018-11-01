package com.zt.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author keets
 */
public interface MsgSource {
    String OUTPUT = "msg-output";

    @Output(OUTPUT)
    MessageChannel output();
}
