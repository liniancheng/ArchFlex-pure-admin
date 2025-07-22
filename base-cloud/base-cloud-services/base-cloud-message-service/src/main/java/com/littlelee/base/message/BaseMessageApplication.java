package com.littlelee.base.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: littlelee
 * @date: 2018/11/30 16:45 消息发送中心 处理短信发送 邮件发送
 */
@EnableScheduling
@ComponentScan({"com.littlelee"})
@EnableFeignClients(basePackages = {"com.littlelee.**.service.feign"})
@SpringBootApplication
public class BaseMessageApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseMessageApplication.class, args);
	}
}
