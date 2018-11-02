package org.springframework.cloud.stream.binder.rocket.provisioning;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cloud.stream.binder.ExtendedConsumerProperties;
import org.springframework.cloud.stream.binder.ExtendedProducerProperties;
import org.springframework.cloud.stream.binder.rocket.properties.*;
import org.springframework.cloud.stream.binder.rocket.utils.RocketMQTopicUtils;
import org.springframework.cloud.stream.provisioning.ConsumerDestination;
import org.springframework.cloud.stream.provisioning.ProducerDestination;
import org.springframework.cloud.stream.provisioning.ProvisioningProvider;
import org.springframework.retry.RetryOperations;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class RocketExchangeQueueProvisioner implements ProvisioningProvider<ExtendedConsumerProperties<RocketConsumerProperties>,
        ExtendedProducerProperties<RocketProducerProperties>>, InitializingBean {

    private final Log logger = LogFactory.getLog(getClass());

    private final RocketMQBinderConfigurationProperties configurationProperties;

//	private final AdminUtilsOperation adminUtilsOperation;

    private RetryOperations metadataRetryOperations;

    public RocketExchangeQueueProvisioner(RocketMQBinderConfigurationProperties binderConfigurationProperties) {
        this.configurationProperties = binderConfigurationProperties;
    }

    /**
     * @param metadataRetryOperations the retry configuration
     */
    public void setMetadataRetryOperations(RetryOperations metadataRetryOperations) {
        this.metadataRetryOperations = metadataRetryOperations;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.metadataRetryOperations == null) {
            RetryTemplate retryTemplate = new RetryTemplate();

            SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
            simpleRetryPolicy.setMaxAttempts(10);
            retryTemplate.setRetryPolicy(simpleRetryPolicy);

            ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
            backOffPolicy.setInitialInterval(100);
            backOffPolicy.setMultiplier(2);
            backOffPolicy.setMaxInterval(1000);
            retryTemplate.setBackOffPolicy(backOffPolicy);
            this.metadataRetryOperations = retryTemplate;
        }
    }

    @Override
    public ProducerDestination provisionProducerDestination(final String name, ExtendedProducerProperties<RocketProducerProperties> properties) {
        if (this.logger.isInfoEnabled()) {
            this.logger.info("Using topic for outbound: " + name);
        }
        RocketMQTopicUtils.validateTopicName(name);
        createTopicsIfAutoCreateEnabledAndAdminUtilsPresent(name, properties.getPartitionCount(), false);
//		if (this.configurationProperties.isAutoCreateTopics() && adminUtilsOperation != null) {
//			final ZkUtils zkUtils = ZkUtils.apply(this.configurationProperties.getNameSrvConnectionString(),
//					this.configurationProperties.getNameSrvSessionTimeout(),
//					this.configurationProperties.getNameSrvConnectionTimeout(),
//					JaasUtils.isZkSecurityEnabled());
//			int partitions = adminUtilsOperation.partitionSize(name, zkUtils);
//			return new RocketMQProducerDestination(name, partitions);
//		}
//		else {
//			return new RocketMQProducerDestination(name);
//		}
        return new RocketMQProducerDestination(name);
    }

    @Override
    public ConsumerDestination provisionConsumerDestination(final String name, final String group, ExtendedConsumerProperties<RocketConsumerProperties> properties) {
        RocketMQTopicUtils.validateTopicName(name);
        boolean anonymous = !StringUtils.hasText(group);
        Assert.isTrue(!anonymous || !properties.getExtension().isEnableDlq(),
                "DLQ support is not available for anonymous subscriptions");
        if (properties.getInstanceCount() == 0) {
            throw new IllegalArgumentException("Instance count cannot be zero");
        }
        int partitionCount = properties.getInstanceCount() * properties.getConcurrency();
        createTopicsIfAutoCreateEnabledAndAdminUtilsPresent(name, partitionCount, properties.getExtension().isAutoRebalanceEnabled());
        return new RocketMQConsumerDestination(name);
    }

    private void createTopicsIfAutoCreateEnabledAndAdminUtilsPresent(final String topicName, final int partitionCount,
                                                                     boolean tolerateLowerPartitionsOnBroker) {
        logger.info("(this.configurationProperties:" + this.configurationProperties);
//		System.out.println("adminUtilsOperation:" + adminUtilsOperation);
        if (this.configurationProperties.isAutoCreateTopics()) {
            //createTopicAndPartitions(topicName, partitionCount, tolerateLowerPartitionsOnBroker);
        } else if (this.configurationProperties.isAutoCreateTopics()) {
            this.logger.warn("Auto creation of topics is enabled, but AdminUtils class is not present on the classpath. " +
                    "No topic will be created by the binder");
        } else if (!this.configurationProperties.isAutoCreateTopics()) {
            this.logger.info("Auto creation of topics is disabled.");
        }
    }

    private static final class RocketMQProducerDestination implements ProducerDestination {

        private final String producerDestinationName;

        private final int partitions;

        RocketMQProducerDestination(String destinationName) {
            this(destinationName, 0);
        }

        RocketMQProducerDestination(String destinationName, Integer partitions) {
            this.producerDestinationName = destinationName;
            this.partitions = partitions;
        }

        @Override
        public String getName() {
            return producerDestinationName;
        }

        @Override
        public String getNameForPartition(int partition) {
            return producerDestinationName;
        }

        @Override
        public String toString() {
            return "RocketMQProducerDestination{" +
                    "producerDestinationName='" + producerDestinationName + '\'' +
                    ", partitions=" + partitions +
                    '}';
        }
    }

    private static final class RocketMQConsumerDestination implements ConsumerDestination {

        private final String consumerDestinationName;

        private final int partitions;

        private final String dlqName;

        RocketMQConsumerDestination(String consumerDestinationName) {
            this(consumerDestinationName, 0, null);
        }

        RocketMQConsumerDestination(String consumerDestinationName, int partitions) {
            this(consumerDestinationName, partitions, null);
        }

        RocketMQConsumerDestination(String consumerDestinationName, Integer partitions, String dlqName) {
            this.consumerDestinationName = consumerDestinationName;
            this.partitions = partitions;
            this.dlqName = dlqName;
        }

        @Override
        public String getName() {
            return this.consumerDestinationName;
        }

        @Override
        public String toString() {
            return "RocketMQConsumerDestination{" +
                    "consumerDestinationName='" + consumerDestinationName + '\'' +
                    ", partitions=" + partitions +
                    ", dlqName='" + dlqName + '\'' +
                    '}';
        }
    }
}
