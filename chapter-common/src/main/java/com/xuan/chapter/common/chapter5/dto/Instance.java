package com.xuan.chapter.common.chapter5.dto;

/**
 * Created by xuan on 2018/2/3.
 */
public class Instance {


    private String serviceId;

    private String host;

    private int port;

    // 保留默认构造函数，防止feign反序列化失败
    public Instance() {

    }

    public Instance(String serviceId, String host, int port) {
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
    }


    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public String toString() {

        return "Instance serviceId is " + this.serviceId +
                ", host is " + this.host +
                ", port is " + this.port;


    }
}
