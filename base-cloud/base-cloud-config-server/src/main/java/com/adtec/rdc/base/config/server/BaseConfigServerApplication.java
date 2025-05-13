package com.adtec.rdc.base.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableConfigServer
@SpringBootApplication
@EnableScheduling
@ComponentScan({"com.adtec.rdc"})
public class BaseConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseConfigServerApplication.class, args);
    }
}
