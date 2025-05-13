package com.adtec.rdc.base.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableEurekaServer
@SpringBootApplication
@ComponentScan({"com.adtec.rdc"})
public class BaseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseServerApplication.class, args);
    }
}
