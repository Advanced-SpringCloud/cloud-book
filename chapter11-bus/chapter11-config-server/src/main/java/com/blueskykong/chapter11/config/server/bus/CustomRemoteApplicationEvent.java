package com.blueskykong.chapter11.config.server.bus;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

public class CustomRemoteApplicationEvent extends RemoteApplicationEvent {

    private CustomRemoteApplicationEvent() {
    }

    public CustomRemoteApplicationEvent(String content, String originService,
                                        String destinationService) {
        super(content, originService, destinationService);
    }

}
