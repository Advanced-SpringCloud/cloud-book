package com.remcarpediem.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SelfDefinition {
    @Input("input")
    SubscribableChannel input();
    @Output("output")
    MessageChannel output();
}
