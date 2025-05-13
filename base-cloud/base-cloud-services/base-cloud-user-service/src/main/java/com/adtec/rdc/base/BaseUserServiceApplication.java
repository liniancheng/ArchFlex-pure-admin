package com.adtec.rdc.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
@ImportResource(locations = { "classpath:kaptchaConfig.xml" })
@ComponentScan({"com.adtec.rdc"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
public class BaseUserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseUserServiceApplication.class, args);
	}
}
