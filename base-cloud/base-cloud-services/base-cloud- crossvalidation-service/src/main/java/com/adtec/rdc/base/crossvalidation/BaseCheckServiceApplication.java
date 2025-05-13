package com.adtec.rdc.base.crossvalidation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.adtec.rdc"})
@MapperScan({"com.adtec.rdc.base.*.mapper"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
public class BaseCheckServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseCheckServiceApplication.class, args);
	}
}
