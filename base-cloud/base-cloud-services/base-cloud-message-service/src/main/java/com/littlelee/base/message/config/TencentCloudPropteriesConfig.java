package com.littlelee.base.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: littlelee
 * @date: 2018/12/3 10:01
 */
@Data
@Component
@ConfigurationProperties(prefix = "sms.tencent-cloud")
public class TencentCloudPropteriesConfig {

    /**
     * appId
     */
    private Integer appId;

    /**
     * appKey
     */
    private String appKey;

}
