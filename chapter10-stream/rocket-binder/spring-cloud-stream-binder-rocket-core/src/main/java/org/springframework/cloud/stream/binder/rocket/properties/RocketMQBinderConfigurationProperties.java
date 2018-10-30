package org.springframework.cloud.stream.binder.rocket.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@ConfigurationProperties(prefix = "spring.cloud.stream.rocketmq.binder")
public class RocketMQBinderConfigurationProperties {

    private String[] nameSrvAddr = new String[]{"localhost"};

    private String defaultNameSrvPort = "9876";

    private Map<String, String> configuration = new HashMap<>();

    private String[] brokers = new String[]{"localhost"};

    private String defaultBrokerPort = "9092";

    private String[] headers = new String[]{};

    private int offsetUpdateTimeWindow = 10000;

    private int offsetUpdateCount;

    private int offsetUpdateShutdownTimeout = 2000;

    private int maxWait = 100;

    private boolean autoCreateTopics = true;

    private boolean autoAddPartitions;

    private int socketBufferSize = 2097152;

    /**
     * session timeout in milliseconds.
     */
    private int nameSrvSessionTimeout = 10000;

    /**
     * Connection timeout in milliseconds.
     */
    private int nameSrvConnectionTimeout = 10000;

    private int requiredAcks = 1;

    private int replicationFactor = 1;

    private int fetchSize = 1024 * 1024;

    private int minPartitionCount = 1;

    private int queueSize = 8192;

    /**
     * Time to wait to get partition information in seconds; default 60.
     */
    private int healthTimeout = 60;


    public String getNameSrvConnectionString() {
        return toConnectionString(this.nameSrvAddr, this.defaultNameSrvPort);
    }

    public String getRocketMQConnectionString() {
        return toConnectionString(this.brokers, this.defaultBrokerPort);
    }

    public String[] getHeaders() {
        return this.headers;
    }

    public int getOffsetUpdateTimeWindow() {
        return this.offsetUpdateTimeWindow;
    }

    public int getOffsetUpdateCount() {
        return this.offsetUpdateCount;
    }

    public int getOffsetUpdateShutdownTimeout() {
        return this.offsetUpdateShutdownTimeout;
    }

    public String[] getNameSrvAddr() {
        return nameSrvAddr;
    }

    public void setNameSrvAddr(String[] nameSrvAddr) {
        this.nameSrvAddr = nameSrvAddr;
    }

    public String getDefaultNameSrvPort() {
        return defaultNameSrvPort;
    }

    public String getDefaultBrokerPort() {
        return defaultBrokerPort;
    }

    public void setDefaultNameSrvPort(String defaultNameSrvPort) {
        this.defaultNameSrvPort = defaultNameSrvPort;
    }

    public String[] getBrokers() {
        return this.brokers;
    }

    public void setBrokers(String... brokers) {
        this.brokers = brokers;
    }

    public void setDefaultBrokerPort(String defaultBrokerPort) {
        this.defaultBrokerPort = defaultBrokerPort;
    }

    public void setHeaders(String... headers) {
        this.headers = headers;
    }

    public void setOffsetUpdateTimeWindow(int offsetUpdateTimeWindow) {
        this.offsetUpdateTimeWindow = offsetUpdateTimeWindow;
    }

    public void setOffsetUpdateCount(int offsetUpdateCount) {
        this.offsetUpdateCount = offsetUpdateCount;
    }

    public void setOffsetUpdateShutdownTimeout(int offsetUpdateShutdownTimeout) {
        this.offsetUpdateShutdownTimeout = offsetUpdateShutdownTimeout;
    }

    public int getNameSrvSessionTimeout() {
        return this.nameSrvSessionTimeout;
    }

    public void setNameSrvSessionTimeout(int nameSrvSessionTimeout) {
        this.nameSrvSessionTimeout = nameSrvSessionTimeout;
    }

    public int getNameSrvConnectionTimeout() {
        return this.nameSrvConnectionTimeout;
    }

    public void setNameSrvConnectionTimeout(int nameSrvConnectionTimeout) {
        this.nameSrvConnectionTimeout = nameSrvConnectionTimeout;
    }

    /**
     * Converts an array of host values to a comma-separated String.
     *
     * It will append the default port value, if not already specified.
     */
    private String toConnectionString(String[] hosts, String defaultPort) {
        String[] fullyFormattedHosts = new String[hosts.length];
        for (int i = 0; i < hosts.length; i++) {
            if (hosts[i].contains(":") || StringUtils.isEmpty(defaultPort)) {
                fullyFormattedHosts[i] = hosts[i];
            } else {
                fullyFormattedHosts[i] = hosts[i] + ":" + defaultPort;
            }
        }
        return StringUtils.arrayToDelimitedString(fullyFormattedHosts, ";");
    }

    public int getMaxWait() {
        return this.maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getRequiredAcks() {
        return this.requiredAcks;
    }

    public void setRequiredAcks(int requiredAcks) {
        this.requiredAcks = requiredAcks;
    }

    public int getReplicationFactor() {
        return this.replicationFactor;
    }

    public void setReplicationFactor(int replicationFactor) {
        this.replicationFactor = replicationFactor;
    }

    public int getFetchSize() {
        return this.fetchSize;
    }

    public void setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
    }

    public int getMinPartitionCount() {
        return this.minPartitionCount;
    }

    public void setMinPartitionCount(int minPartitionCount) {
        this.minPartitionCount = minPartitionCount;
    }

    public int getHealthTimeout() {
        return this.healthTimeout;
    }

    public void setHealthTimeout(int healthTimeout) {
        this.healthTimeout = healthTimeout;
    }

    public int getQueueSize() {
        return this.queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public boolean isAutoCreateTopics() {
        return this.autoCreateTopics;
    }

    public void setAutoCreateTopics(boolean autoCreateTopics) {
        this.autoCreateTopics = autoCreateTopics;
    }

    public boolean isAutoAddPartitions() {
        return this.autoAddPartitions;
    }

    public void setAutoAddPartitions(boolean autoAddPartitions) {
        this.autoAddPartitions = autoAddPartitions;
    }

    public int getSocketBufferSize() {
        return this.socketBufferSize;
    }

    public void setSocketBufferSize(int socketBufferSize) {
        this.socketBufferSize = socketBufferSize;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public Map<String, Object> getConsumerConfiguration() {
        Map<String, Object> consumerConfiguration = new HashMap<>();

//		// Copy configured binder properties
//		for (Map.Entry<String, String> configurationEntry : this.configuration.entrySet()) {
//			if (ConsumerConfig.configNames().contains(configurationEntry.getKey())) {
//				consumerConfiguration.put(configurationEntry.getKey(), configurationEntry.getValue());
//			}
//		}
//		// Override Spring Boot bootstrap server setting if left to default with the value
//		// configured in the binder
//		if (ObjectUtils.isEmpty(consumerConfiguration.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG))) {
//			consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getRocketMQConnectionString());
//		}
//		else {
//			Object boostrapServersConfig = consumerConfiguration.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);
//			if (boostrapServersConfig instanceof List) {
//				@SuppressWarnings("unchecked")
//				List<String> bootStrapServers = (List<String>) consumerConfiguration
//						.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);
//				if (bootStrapServers.size() == 1 && bootStrapServers.get(0).equals("localhost:9092")) {
//					consumerConfiguration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getRocketMQConnectionString());
//				}
//			}
//		}
        return Collections.unmodifiableMap(consumerConfiguration);
    }

    public Map<String, Object> getProducerConfiguration() {
        Map<String, Object> producerConfiguration = new HashMap<>();

//		// Copy configured binder properties
//		for (Map.Entry<String, String> configurationEntry : configuration.entrySet()) {
//			if (ProducerConfig.configNames().contains(configurationEntry.getKey())) {
//				producerConfiguration.put(configurationEntry.getKey(), configurationEntry.getValue());
//			}
//		}
//		// Override Spring Boot bootstrap server setting if left to default with the value
//		// configured in the binder
//		if (ObjectUtils.isEmpty(producerConfiguration.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG))) {
//			producerConfiguration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getRocketMQConnectionString());
//		}
//		else {
//			Object boostrapServersConfig = producerConfiguration.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
//			if (boostrapServersConfig instanceof List) {
//				@SuppressWarnings("unchecked")
//				List<String> bootStrapServers = (List<String>) producerConfiguration
//						.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);
//				if (bootStrapServers.size() == 1 && bootStrapServers.get(0).equals("localhost:9092")) {
//					producerConfiguration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getRocketMQConnectionString());
//				}
//			}
//		}
        return Collections.unmodifiableMap(producerConfiguration);
    }

    @Override
    public String toString() {
        return "RocketMQBinderConfigurationProperties{" +
                "nameSrvAddr=" + Arrays.toString(nameSrvAddr) +
                ", defaultNameSrvPort='" + defaultNameSrvPort + '\'' +
                ", configuration=" + configuration +
                ", brokers=" + Arrays.toString(brokers) +
                ", defaultBrokerPort='" + defaultBrokerPort + '\'' +
                ", headers=" + Arrays.toString(headers) +
                ", offsetUpdateTimeWindow=" + offsetUpdateTimeWindow +
                ", offsetUpdateCount=" + offsetUpdateCount +
                ", offsetUpdateShutdownTimeout=" + offsetUpdateShutdownTimeout +
                ", maxWait=" + maxWait +
                ", autoCreateTopics=" + autoCreateTopics +
                ", autoAddPartitions=" + autoAddPartitions +
                ", socketBufferSize=" + socketBufferSize +
                ", nameSrvSessionTimeout=" + nameSrvSessionTimeout +
                ", nameSrvConnectionTimeout=" + nameSrvConnectionTimeout +
                ", requiredAcks=" + requiredAcks +
                ", replicationFactor=" + replicationFactor +
                ", fetchSize=" + fetchSize +
                ", minPartitionCount=" + minPartitionCount +
                ", queueSize=" + queueSize +
                ", healthTimeout=" + healthTimeout +
                '}';
    }
}

