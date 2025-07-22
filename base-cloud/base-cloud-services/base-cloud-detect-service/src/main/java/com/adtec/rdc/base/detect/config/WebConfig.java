package com.adtec.rdc.base.detect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 原图路径映射
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/D:/work/tobacco/upload/");

        // 将 D:\work\tobacco\result 映射为 http://localhost:8080/images/ 访问路径
        registry.addResourceHandler("/result/**")
                .addResourceLocations("file:/D:/work/tobacco/result/");
    }
}
