package com.blueskykong.chapter11.config.server.bus;

import org.slf4j.LoggerFactory;
import org.springframework.cloud.bus.event.EnvironmentChangeRemoteApplicationEvent;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
@RemoteApplicationEventScan(basePackageClasses = CustomRemoteApplicationEvent.class)
//@RemoteApplicationEventScan(basePackages = {"com.blueskykong.chapter9.config.server.bus"})
//@RemoteApplicationEventScan({"com.blueskykong.chapter9.config.server.bus"})
public class BusConfiguration {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(BusConfiguration.class);

    @EventListener
    public void onUserRemoteApplicationEvent(CustomRemoteApplicationEvent event) {

        logger.info("CustomRemoteApplicationEvent:" +
                        " Source: {} , originService: {} , destinationService: {} \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());
    }


    @EventListener
    public void onRefreshRemoteApplicationEvent(RefreshRemoteApplicationEvent event) {

        logger.info("RefreshRemoteApplicationEvent: " +
                        " Source: {} , originService: {} , destinationService: {} \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());

    }

    @EventListener
    public void onEnvironmentChangeRemoteApplicationEvent(EnvironmentChangeRemoteApplicationEvent event) {

        logger.info("EnvironmentChangeRemoteApplicationEvent: " +
                        " Source: {} , originService: {} , destinationService: {} \n",
                event.getSource(),
                event.getOriginService(),
                event.getDestinationService());

    }
}
