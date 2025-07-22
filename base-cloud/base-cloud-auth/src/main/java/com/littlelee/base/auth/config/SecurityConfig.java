package com.littlelee.base.auth.config;

import cn.hutool.core.collection.CollectionUtil;
import com.littlelee.base.auth.captcha.CaptchaFilter;
import com.littlelee.base.auth.handler.MyAuthenticationFailureHandler;
import com.littlelee.base.auth.handler.MyAuthenticationSuccessHandler;
import com.littlelee.base.common.config.IgnoreUrlPropertiesConfig;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;

/**
 * 默认安全配置
 */
@ConfigurationProperties(prefix = "security")
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 白名单路径列表
     */
    @Setter
    private List<String> whitelistPaths;

    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig;

    /**
     * Spring Security 安全过滤器链配置
     *
     * @param http 安全配置
     * @return 安全过滤器链
     */
    @Bean
    @Order(0)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.addFilterBefore(captchaFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests((requests) ->
                        {
                            if (CollectionUtil.isNotEmpty(whitelistPaths)) {
                                for (String whitelistPath : whitelistPaths) {

                                    requests.requestMatchers(mvcMatcherBuilder.pattern(whitelistPath)).permitAll();
                                }
                            }
                            // 动态加载白名单路径
                            ignoreUrlPropertiesConfig.getUrls().forEach(e -> {
                                System.out.println("白名单" + ignoreUrlPropertiesConfig.getUrls());
                                requests.requestMatchers(mvcMatcherBuilder.pattern(e)).permitAll();
                            });
                            requests.anyRequest().authenticated();
                        }
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults());

        return http.build();
    }


    /**
     * 不走过滤器链的放行配置
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                AntPathRequestMatcher.antMatcher("/token/**"),
                AntPathRequestMatcher.antMatcher("/auth/**"),
                AntPathRequestMatcher.antMatcher("/webjars/**"),
                AntPathRequestMatcher.antMatcher("/doc.html"),
                AntPathRequestMatcher.antMatcher("/swagger-resources/**"),
                AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                AntPathRequestMatcher.antMatcher("/swagger-ui/**")
        );
    }

    @Bean
    public CaptchaFilter captchaFilter() {
        CaptchaFilter captchaFilter = new CaptchaFilter();
        captchaFilter.setFailureHandler(authenticationFailureHandler);
        captchaFilter.setRedisTemplate(stringRedisTemplate);
        return  captchaFilter;
    }


}