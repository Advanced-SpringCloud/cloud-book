package com.xuan.chapter12.resource.server.controller;

import com.xuan.chapter.common.chapter5.dto.Instance;
import com.xuan.chapter12.resource.server.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

/**
 * @author xuan
 * @create 2018-02-19 22:25
 **/

@RestController
public class ResourceController {


    private final static Logger logger = LoggerFactory.getLogger(ResourceController.class);
    BearerTokenExtractor tokenExtractor = new BearerTokenExtractor();

    private final static String DEFAULT_NAME = "xuan";
    private static String DEFAULT_SERVICE_ID = "application";
    private static String DEFAULT_HOST = "localhost";
    private static int DEFAULT_PORT = 8080;


    @Autowired
    ResourceServerTokenServices tokenServices;


    // 拦截资源
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public User getUserByUserId(@PathVariable("userId") String userId) {
        logger.info("Get User by UserId {}", userId);
        return new User(userId, DEFAULT_NAME);
    }

    // 不拦截资源
    @RequestMapping(value = "/instance/{serviceId}", method = RequestMethod.GET)
    public Instance getInstanceByServiceId(@PathVariable("serviceId") String serviceId) {
        logger.info("Get Instance by serviceId {}", serviceId);
        return new Instance(serviceId, DEFAULT_HOST, DEFAULT_PORT);
    }


    @RequestMapping(value = "/user")
    public Principal userInfo(ServletRequest req) throws IOException {

        final HttpServletRequest request = (HttpServletRequest) req;
        Authentication authentication = tokenExtractor.extract(request);
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication oAuth2Authentication = tokenServices.loadAuthentication(token);
        return oAuth2Authentication;

    }


}


