package com.littlelee.base.auth.config;

import com.littlelee.base.auth.security.UserDetailsImpl;
import com.littlelee.base.common.base.service.MessageQueueService;
import com.littlelee.base.common.constants.JwtClaimConstants;

import com.littlelee.base.common.constants.MqQueueNameConstant;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.enums.OperationStatusEnum;
import com.littlelee.base.common.enums.SysLogTypeEnum;
import com.littlelee.base.common.model.bo.SysOperlog;
import com.littlelee.base.common.util.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JWT 自定义字段配置
 *
 * @author haoxr
 * @since 3.0.0
 */
@Configuration
public class JwtTokenCustomizerConfig {

    @Autowired
    private MessageQueueService messageQueueService;

    /**
     * JWT 自定义字段
     * @see <a href="https://docs.spring.io/spring-authorization-server/reference/guides/how-to-custom-claims-authorities.html">Add custom claims to JWT access tokens</a>
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) && context.getPrincipal() instanceof UsernamePasswordAuthenticationToken) {
                // Customize headers/claims for access_token
                Optional.ofNullable(context.getPrincipal().getPrincipal()).ifPresent(principal -> {
                    JwtClaimsSet.Builder claims = context.getClaims();
                    if (principal instanceof UserDetailsImpl userDetails) { // 系统用户添加自定义字段

                        claims.claim(JwtClaimConstants.USER_ID, userDetails.getUserId());
                        claims.claim(JwtClaimConstants.USERNAME, userDetails.getUsername());

                        // 这里存入角色至JWT，解析JWT的角色用于鉴权的位置: ResourceServerConfig#jwtAuthenticationConverter
                        var authorities = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities())
                                .stream()
                                .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                        claims.claim(JwtClaimConstants.AUTHORITIES, authorities);

                        // 登录日志记录
                        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                        SysOperlog operlog = new SysOperlog();
                        operlog
                                .setCreateBy(userDetails.getUsername())
                                .setRequestUri(request.getRequestURI())
                                .setUserAgent(request.getHeader("user-agent"))
                                .setLogType(SysLogTypeEnum.LOGIN.getCode())
                                .setLogStatus(OperationStatusEnum.SUCCESS.getCode())
                                .setModuleName("auth认证模块")
                                .setActionName("登录")
                                .setServiceId(ServiceNameConstants.BASE_CLOUD_AUTH)
                                .setRemoteAddr(UrlUtil.getRemoteHost(request))
                                .setMethodName(request.getMethod());
                        messageQueueService.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, operlog);

                    }
                });
            }
        };
    }

}
