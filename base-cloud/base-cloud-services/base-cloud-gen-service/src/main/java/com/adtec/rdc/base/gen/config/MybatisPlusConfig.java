package com.adtec.rdc.base.gen.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: JTao
 * @date: 2018/10/8 16:15
 * @description: mybatis plus 配置
 */
@Configuration
@MapperScan("com.adtec.rdc.base.gen.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
