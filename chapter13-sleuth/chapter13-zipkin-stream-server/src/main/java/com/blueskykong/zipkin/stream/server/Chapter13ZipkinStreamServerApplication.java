package com.blueskykong.zipkin.stream.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@SpringBootApplication
@EnableZipkinStreamServer
public class Chapter13ZipkinStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Chapter13ZipkinStreamServerApplication.class, args);
    }
}
