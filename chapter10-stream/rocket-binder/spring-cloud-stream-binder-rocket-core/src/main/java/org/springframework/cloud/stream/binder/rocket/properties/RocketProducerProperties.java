package org.springframework.cloud.stream.binder.rocket.properties;

import java.util.HashMap;
import java.util.Map;

public class RocketProducerProperties extends RocketCommonProperties {

    private String groupName;

    private int bufferSize = 16384;

    private boolean sync;

    private int batchTimeout;

    private Map<String, String> configuration = new HashMap<>();

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isSync() {
        return this.sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public int getBatchTimeout() {
        return this.batchTimeout;
    }

    public void setBatchTimeout(int batchTimeout) {
        this.batchTimeout = batchTimeout;
    }

    public Map<String, String> getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
