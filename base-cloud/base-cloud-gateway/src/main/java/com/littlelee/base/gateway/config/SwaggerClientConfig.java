package com.littlelee.base.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: littlelee
 * @date: 2018/11/13 14:44
 */
@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerClientConfig {
    private List<String> client = new ArrayList<>();
}
