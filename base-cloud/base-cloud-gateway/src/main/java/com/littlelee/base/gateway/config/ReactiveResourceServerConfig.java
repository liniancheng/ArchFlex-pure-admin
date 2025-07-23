package com.littlelee.base.gateway.config;

import com.littlelee.base.common.config.IgnoreUrlPropertiesConfig;
import com.littlelee.base.gateway.auth.endpoint.ReactiveAuthExceptionEntryPoint;
import com.littlelee.base.gateway.auth.handler.ReactiveCustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @author: JTao
 * @description: Spring Cloud Gateway + OAuth2 资源服务器 Reactive 配置
 */
@Configuration
@EnableWebFluxSecurity
public class ReactiveResourceServerConfig {

    private final IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig;
    private final ServerAuthenticationEntryPoint authEntryPoint;
    private final ServerAccessDeniedHandler accessDeniedHandler;

    public ReactiveResourceServerConfig(IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig,
                                        ServerAuthenticationEntryPoint authEntryPoint,
                                        ServerAccessDeniedHandler accessDeniedHandler) {
        this.ignoreUrlPropertiesConfig = ignoreUrlPropertiesConfig;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    /**
     * 统一跨域配置
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    /**
     * Reactive OAuth2 资源服务器主配置
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            ReactiveAuthExceptionEntryPoint authEntryPoint,
                                                            ReactiveCustomAccessDeniedHandler accessDeniedHandler) {


        // 1. 白名单放行
        ignoreUrlPropertiesConfig.getUrls()
                .forEach(url -> http.authorizeExchange(ex -> ex.pathMatchers(url).permitAll()));

        http
                // 2. 关闭 CSRF
                .csrf(csrf -> csrf.disable())
                // 3. 跨域
                .cors(cors -> {})
                // 4. 剩余授权规则
                .authorizeExchange(ex -> ex
                        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyExchange().authenticated())
                // 5. 异常处理
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                // 6. OAuth2 资源服务器 (JWT)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter()
                                        )
                                )
                        )
                );

        return http.build();
    }

    /**
     * 把 JWT 转为 Authentication（与旧版 JwtAccessTokenConverter 类似）
     * 如果之前用了 RedisTokenStore，需要把 JWT 换成 Redis 校验，可参考 ReactiveRedisTemplate 实现
     */
    @Bean
    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // 如有自定义转换器，在这儿设置
        return converter;
    }

    /**
     * 如果需要继续使用 Redis 存 token（而不是 JWT 方式），
     * 可以注入 ReactiveRedisTemplate 自定义 ReactiveAuthenticationManager，
     * 这里给出模板，可按需替换
     */
    @Bean
    public ReactiveRedisTemplate<String, String> reactiveRedisTemplate(
            ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory,
                org.springframework.data.redis.serializer.RedisSerializationContext.string());
    }
}