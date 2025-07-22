package com.littlelee.base.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableEurekaServer
@SpringBootApplication
@ComponentScan({"com.littlelee"})
public class BaseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseServerApplication.class, args);
    }
}
