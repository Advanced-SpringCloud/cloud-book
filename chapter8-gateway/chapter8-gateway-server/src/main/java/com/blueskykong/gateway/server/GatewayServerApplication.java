package com.blueskykong.gateway.server;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author keets
 */
@SpringBootApplication
@RestController
public class GatewayServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }

    @RequestMapping("/fallbackcontroller")
    public Map<String, String> fallbackcontroller(@RequestParam("a") String a) {
        return Collections.singletonMap("from", "fallbackcontroller");
    }

    @GetMapping("/proxy/{id}")
    public Mono<ResponseEntity<Object>> proxyFoos(@PathVariable Integer id, ProxyExchange<Object> proxy)
            throws Exception {
        return proxy.uri("http://localhost:9090" + "/foos/" + id).get();
    }

    @GetMapping("/test/123")
    public List<Foo> foos() {
        return Arrays.asList(new Foo("hello"));
    }

    @GetMapping("/foos/{id}")
    public Foo foo(@PathVariable Integer id, @RequestHeader HttpHeaders headers) {
        String custom = headers.getFirst("X-Custom");
        return new Foo(id == 1 ? "foo" : custom != null ? custom : "bye");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Foo {
        private String name;

        public Foo() {
        }

        public Foo(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
