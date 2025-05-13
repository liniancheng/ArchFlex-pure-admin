package com.adtec.rdc.base.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan({"com.adtec.rdc"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
@SpringBootApplication
public class BaseAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseAuthApplication.class, args);
	}
}
