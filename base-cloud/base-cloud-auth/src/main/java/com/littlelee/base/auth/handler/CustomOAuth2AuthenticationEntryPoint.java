package com.littlelee.base.auth.handler;

import com.littlelee.base.common.base.service.MessageQueueService;
import com.littlelee.base.common.constants.MqQueueNameConstant;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.enums.OperationStatusEnum;
import com.littlelee.base.common.enums.SysLogTypeEnum;
import com.littlelee.base.common.model.bo.SysOperlog;
import com.littlelee.base.common.util.UrlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Spring Authorization Server 6.x
 * 等价于旧的 WebResponseExceptionTranslator
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final MessageQueueService messageQueueService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // 记录失败日志
        String username = request.getParameter("username");
        SysOperlog operlog = new SysOperlog()
                .setCreateBy(username == null ? "unknown" : username)
                .setRequestUri(request.getRequestURI())
                .setUserAgent(request.getHeader("user-agent"))
                .setLogType(SysLogTypeEnum.LOGIN.getCode())
                .setLogStatus(OperationStatusEnum.FAIL.getCode())
                .setModuleName("auth认证模块")
                .setActionName("登录")
                .setServiceId(ServiceNameConstants.BASE_CLOUD_AUTH)
                .setRemoteAddr(UrlUtil.getRemoteHost(request))
                .setMethodName(request.getMethod())
                .setExceptionStr(UrlUtil.getTrace(authException));
        messageQueueService.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, operlog);

        log.error("OAuth2 认证异常", authException);

        // 统一返回 JSON
        OAuth2Error error = (authException instanceof OAuth2AuthenticationException oae)
                ? oae.getError()
                : new OAuth2Error("invalid_request", authException.getMessage(), null);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                Map.of(
                        "error", error.getErrorCode(),
                        "error_description", error.getDescription()
                )
        ));
    }
}