package com.adtec.rdc.base.detect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.adtec.rdc"})
@MapperScan({"com.adtec.rdc.base.*.mapper"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
public class BaseDetectServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseDetectServiceApplication.class, args);
	}
}
