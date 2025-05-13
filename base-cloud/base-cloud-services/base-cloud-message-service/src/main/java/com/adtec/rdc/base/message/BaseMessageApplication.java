package com.adtec.rdc.base.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: JTao
 * @date: 2018/11/30 16:45 消息发送中心 处理短信发送 邮件发送
 */
@EnableScheduling
@ComponentScan({"com.adtec.rdc"})
@EnableFeignClients(basePackages = {"com.adtec.rdc.**.service.feign"})
@SpringBootApplication
public class BaseMessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseMessageApplication.class, args);
	}
}
