package com.littlelee.base.detect;

import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan({"com.littlelee"})
@MapperScan({"com.littlelee.base.*.mapper"})
@EnableFeignClients(basePackages = {"com.littlelee.**.service.feign"})
public class BaseDetectServiceApplication {

	@Value("${spring.ai.ollama.base-url}")
	private String ollamaBaseUrl;

	public static void main(String[] args) {
		SpringApplication.run(BaseDetectServiceApplication.class, args);
	}

	@PostConstruct
	public void printOllamaUrl() {
		System.out.println(">>> spring.ai.ollama.base-url = " + ollamaBaseUrl);
	}
}
