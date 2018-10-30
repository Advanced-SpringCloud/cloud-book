package org.springframework.cloud.stream.binder.rocket.properties;

import org.springframework.cloud.stream.binder.HeaderMode;

import java.util.HashMap;
import java.util.Map;

public class RocketConsumerProperties extends RocketCommonProperties {
    private boolean autoRebalanceEnabled = true;

    private boolean autoCommitOffset = true;

    private Boolean autoCommitOnError;

    private String startOffset;


    private boolean enableDlq;

    private String dlqName;

    private int recoveryInterval = 5000;


    private Map<String, String> configuration = new HashMap<>();

    private HeaderMode headerMode = HeaderMode.raw;

    public boolean isAutoCommitOffset() {
        return this.autoCommitOffset;
    }

    public void setAutoCommitOffset(boolean autoCommitOffset) {
        this.autoCommitOffset = autoCommitOffset;
    }

    public String getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(String startOffset) {
        this.startOffset = startOffset;
    }

    public boolean isEnableDlq() {
        return this.enableDlq;
    }

    public void setEnableDlq(boolean enableDlq) {
        this.enableDlq = enableDlq;
    }

    public Boolean getAutoCommitOnError() {
        return this.autoCommitOnError;
    }

    public void setAutoCommitOnError(Boolean autoCommitOnError) {
        this.autoCommitOnError = autoCommitOnError;
    }

    public int getRecoveryInterval() {
        return this.recoveryInterval;
    }

    public void setRecoveryInterval(int recoveryInterval) {
        this.recoveryInterval = recoveryInterval;
    }

    public boolean isAutoRebalanceEnabled() {
        return this.autoRebalanceEnabled;
    }

    public void setAutoRebalanceEnabled(boolean autoRebalanceEnabled) {
        this.autoRebalanceEnabled = autoRebalanceEnabled;
    }

    public enum StartOffset {
        earliest(-2L),
        latest(-1L);

        private final long referencePoint;

        StartOffset(long referencePoint) {
            this.referencePoint = referencePoint;
        }

        public long getReferencePoint() {
            return this.referencePoint;
        }
    }

    public Map<String, String> getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }

    public String getDlqName() {
        return dlqName;
    }

    public void setDlqName(String dlqName) {
        this.dlqName = dlqName;
    }


    public HeaderMode getHeaderMode() {
        return this.headerMode;
    }

    public void setHeaderMode(HeaderMode headerMode) {
        this.headerMode = headerMode;
    }
}
