package com.adtec.rdc.base.gateway.auth.endpoint;

import com.adtec.rdc.base.common.enums.ResponseCodeEnum;
import com.adtec.rdc.base.common.util.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: JTao
 * @date: 2018/10/12 10:31
 * @description: 自定义AuthExceptionEntryPoint用于tokan校验失败返回信息
 */
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        ApiResult<String> result = new ApiResult<>(authException, ResponseCodeEnum.PERMISSION_DEFINED);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
