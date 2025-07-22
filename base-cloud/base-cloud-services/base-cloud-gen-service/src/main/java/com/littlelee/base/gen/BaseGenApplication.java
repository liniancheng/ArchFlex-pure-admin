package com.littlelee.base.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: littlelee
 * @date: 2018/11/8 09:23
 */
@EnableScheduling
@ComponentScan({"com.littlelee"})
@EnableFeignClients(basePackages = {"com.littlelee.**.service.feign"})
@SpringBootApplication
public class BaseGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseGenApplication.class, args);
    }
}
