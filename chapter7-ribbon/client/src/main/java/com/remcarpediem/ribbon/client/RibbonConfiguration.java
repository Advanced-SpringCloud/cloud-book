package com.remcarpediem.ribbon.client;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author libing.zt
 * @version $Id: RibbonConfiguration.java, v 0.1 2018年02月17日 下午8:30 libing.zt Exp $
 */
public class RibbonConfiguration {

    @Autowired
    IClientConfig ribbonClientConfig;

    // 使用该IPing会导致ZoneAwareLoadbalancer中的uplist为0，从而导致no instance
//    @Bean
//    public IPing ribbonPing() {
//        return new PingUrl();
//    }

    @Bean
    public IRule ribbonRule() {
        // 负载均衡规则，改为随机
        return new RandomRule();
    }
}
