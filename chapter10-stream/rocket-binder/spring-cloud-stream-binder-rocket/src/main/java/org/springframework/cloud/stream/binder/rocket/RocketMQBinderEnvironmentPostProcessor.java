/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.binder.rocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RocketMQBinderEnvironmentPostProcessor implements EnvironmentPostProcessor {

    public final static String SPRING_ROCKETMQ = "spring.rocketmq";

    public final static String SPRING_ROCKETMQ_NAMESRV_ADDR = SPRING_ROCKETMQ + ".NAMESRV_ADDR";

    public final static String SPRING_ROCKETMQ_NAMESERVER_ADDRESS = SPRING_ROCKETMQ + ".nameserver_address";

    public final static String SPRING_ROCKETMQ_PRODUCER = SPRING_ROCKETMQ + ".producer";

    public final static String SPRING_ROCKETMQ_CONSUMER = SPRING_ROCKETMQ + ".consumer";

    public final static String SPRING_ROCKETMQ_PRODUCER_KEY_SERIALIZER = SPRING_ROCKETMQ_PRODUCER + "." + "keySerializer";

    public final static String SPRING_ROCKETMQ_PRODUCER_VALUE_SERIALIZER = SPRING_ROCKETMQ_PRODUCER + "." + "valueSerializer";

    public final static String SPRING_ROCKETMQ_CONSUMER_KEY_DESERIALIZER = SPRING_ROCKETMQ_CONSUMER + "." + "keyDeserializer";

    public final static String SPRING_ROCKETMQ_CONSUMER_VALUE_DESERIALIZER = SPRING_ROCKETMQ_CONSUMER + "." + "valueDeserializer";

    private static final String ROCKETMQ_BINDER_DEFAULT_PROPERTIES = "rocketmqBinderDefaultProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (!environment.getPropertySources().contains(ROCKETMQ_BINDER_DEFAULT_PROPERTIES)) {
            Map<String, Object> rocketmqBinderDefaultProperties = new HashMap<>();
            rocketmqBinderDefaultProperties.put("logging.level.org.apache.rocketmq", "ERROR");

            environment.getPropertySources().addLast(new MapPropertySource(ROCKETMQ_BINDER_DEFAULT_PROPERTIES, rocketmqBinderDefaultProperties));
        }
    }
}
