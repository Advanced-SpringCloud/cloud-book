package com.xuan.chapter12.oauth2.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class Chapter12Oauth2ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter12Oauth2ClientApplication.class, args);
    }
}
