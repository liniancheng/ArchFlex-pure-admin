package com.littlelee.base.crossvalidation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.littlelee"})
@MapperScan({"com.littlelee.base.*.mapper"})
@EnableFeignClients(basePackages = {"com.littlelee.**.service.feign"})
public class BaseCheckServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseCheckServiceApplication.class, args);
	}
}
