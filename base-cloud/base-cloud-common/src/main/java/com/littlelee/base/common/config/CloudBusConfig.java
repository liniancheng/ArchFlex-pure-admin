package com.littlelee.base.common.config;

import org.springframework.cloud.bus.event.Destination;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudBusConfig {

    @Bean
    public Destination.Factory destinationFactory() {
        return event -> () -> "default";  // 返回固定的destination字符串
    }
}
