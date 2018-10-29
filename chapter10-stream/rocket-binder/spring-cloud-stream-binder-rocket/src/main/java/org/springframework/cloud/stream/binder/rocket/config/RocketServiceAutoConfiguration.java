package org.springframework.cloud.stream.binder.rocket.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnMissingBean({Binder.class})
@Import({RocketMessageChannelBinderConfiguration.class})
public class RocketServiceAutoConfiguration {

}
