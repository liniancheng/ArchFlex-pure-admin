package com.littlelee.base.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("com.littlelee")
@EnableFeignClients(basePackages = "com.littlelee.**.service.feign")
@SpringBootApplication
public class BaseGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseGatewayApplication.class, args);
	}
}