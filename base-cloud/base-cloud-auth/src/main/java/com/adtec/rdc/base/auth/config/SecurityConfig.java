package com.adtec.rdc.base.auth.config;

import com.adtec.rdc.base.auth.captcha.CaptchaFilter;
import com.adtec.rdc.base.auth.mobile.MobileAuthenticationFilter;
import com.adtec.rdc.base.auth.mobile.MobileAuthenticationProvider;
import com.adtec.rdc.base.auth.security.UserDetailsServiceImpl;
import com.adtec.rdc.base.auth.service.feign.SysUserService;
import com.adtec.rdc.base.common.config.IgnoreUrlPropertiesConfig;
import com.adtec.rdc.base.common.util.CryptUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: JTao
 * @date: 2018/10/9 14:49
 * @description: web security配置
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoreUrlPropertiesConfig ignoreUrlPropertiesConfig;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public AuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
        mobileAuthenticationProvider.setSysUserService(sysUserService);
        mobileAuthenticationProvider.setRedisTemplate(redisTemplate);
        return mobileAuthenticationProvider;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
    	return new CustomEncoder();
    }
 
    /**
     * 自定义加解密
     * @author dengchf
     *
     */
    private class CustomEncoder extends BCryptPasswordEncoder { 
    	@Override
    	public boolean matches(CharSequence rawPassword, String encodedPassword) {
    		String rawCode = CryptUtils.getCBCDesEncrypt(rawPassword.toString(),CryptUtils.AES_KEY,CryptUtils.AES_IV);
    		return super.matches(rawCode, encodedPassword);
    	};
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(mobileAuthenticationProvider())
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
    	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config
                = http.addFilterBefore(captchaFilter(), UsernamePasswordAuthenticationFilter.class)
                .requestMatchers().anyRequest()
                .and()
                .authorizeRequests();
        ignoreUrlPropertiesConfig.getUrls().forEach( e ->{
            config.antMatchers(e).permitAll();
        });
        config
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    /**
     * 自定义登陆过滤器
     * @return
     */
    @Bean
    public MobileAuthenticationFilter mobileAuthenticationFilter() {
        MobileAuthenticationFilter filter = new MobileAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(authenticationFailureHandler);
        return filter;
    }

    @Bean
    public CaptchaFilter captchaFilter() {
        CaptchaFilter captchaFilter = new CaptchaFilter();
        captchaFilter.setFailureHandler(authenticationFailureHandler);
        captchaFilter.setRedisTemplate(stringRedisTemplate);
        return  captchaFilter;
    }

}
