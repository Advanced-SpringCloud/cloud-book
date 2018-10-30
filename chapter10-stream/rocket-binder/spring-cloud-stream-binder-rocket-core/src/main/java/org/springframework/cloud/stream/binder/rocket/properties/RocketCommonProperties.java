package org.springframework.cloud.stream.binder.rocket.properties;

public class RocketCommonProperties {
    private String groupName;

    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
