package org.springframework.cloud.stream.binder.rocket.properties;

import java.util.HashMap;
import java.util.Map;

public class RocketProducerProperties extends RocketCommonProperties {


    private int bufferSize = 16384;

    private boolean sync;

    private int batchTimeout;

    private Map<String, String> configuration = new HashMap<>();

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }

    public int getBatchTimeout() {
        return batchTimeout;
    }

    public void setBatchTimeout(int batchTimeout) {
        this.batchTimeout = batchTimeout;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
}
