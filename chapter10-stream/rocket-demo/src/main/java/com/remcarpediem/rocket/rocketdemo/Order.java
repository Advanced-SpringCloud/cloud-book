package com.remcarpediem.rocket.rocketdemo;

public class Order {
    private Long id;
    private String content;

    public Order(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public Order() {
    }

    public Order(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
