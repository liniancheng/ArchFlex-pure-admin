package com.adtec.rdc.base.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: JTao
 * @date: 2018/11/8 09:23
 */
@EnableScheduling
@ComponentScan({"com.adtec.rdc"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
@SpringBootApplication
public class BaseGenApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseGenApplication.class, args);
    }
}
