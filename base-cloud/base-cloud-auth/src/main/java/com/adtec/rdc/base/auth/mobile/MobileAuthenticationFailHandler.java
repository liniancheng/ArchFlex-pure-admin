package com.adtec.rdc.base.auth.mobile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.adtec.rdc.base.common.enums.ResponseCodeEnum;
import com.adtec.rdc.base.common.util.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: JTao
 * @date: 2018/12/12 17:41
 */
@Slf4j
@Component
public class MobileAuthenticationFailHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    private final static String UTF8 = "utf-8";
    private final static String CONTENT_TYPE = "application/json";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("登录失败 原因: {}", exception.getMessage());
        response.setCharacterEncoding(UTF8);
        response.setContentType(CONTENT_TYPE);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(ApiResult.failed(exception.getMessage())));
    }
}
