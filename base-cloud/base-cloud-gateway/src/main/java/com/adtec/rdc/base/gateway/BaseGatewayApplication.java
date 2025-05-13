package com.adtec.rdc.base.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan({"com.adtec.rdc"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
@EnableZuulProxy
@SpringBootApplication
public class BaseGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseGatewayApplication.class, args);
	}
}
