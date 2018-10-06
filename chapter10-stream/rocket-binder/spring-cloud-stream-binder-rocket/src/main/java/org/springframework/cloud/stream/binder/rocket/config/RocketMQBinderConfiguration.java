package org.springframework.cloud.stream.binder.rocket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.cloud.stream.binder.rocket.RocketMessageChannelBinder;
import org.springframework.cloud.stream.binder.rocket.properties.RocketMQBinderConfigurationProperties;
import org.springframework.cloud.stream.binder.rocket.properties.RocketMQExtendedBindingProperties;
import org.springframework.cloud.stream.binder.rocket.provisioning.RocketExchangeQueueProvisioner;
import org.springframework.cloud.stream.binder.rocket.support.RocketMQResourceManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnMissingBean(Binder.class)
@Import({PropertyPlaceholderAutoConfiguration.class})
@EnableConfigurationProperties({RocketMQBinderConfigurationProperties.class, RocketMQExtendedBindingProperties.class})
public class RocketMQBinderConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RocketMQResourceManager.class);

    @Autowired
    private RocketMQBinderConfigurationProperties configurationProperties;

    @Autowired
    private RocketMQExtendedBindingProperties rocketmqExtendedBindingProperties;

    // @Autowired
    // private ProducerListener producerListener;

    @Autowired
    private ApplicationContext context;

    @Bean
    RocketExchangeQueueProvisioner provisioningProvider() {
        return new RocketExchangeQueueProvisioner(this.configurationProperties);
    }

    @Bean
    RocketMessageChannelBinder rocketMessageChannelBinder() {
        RocketMessageChannelBinder messageChannelBinder = new RocketMessageChannelBinder(
                this.configurationProperties, provisioningProvider());
        // messageChannelBinder.setProducerListener(producerListener);
        messageChannelBinder.setExtendedBindingProperties(this.rocketmqExtendedBindingProperties);
        return messageChannelBinder;
    }

}
