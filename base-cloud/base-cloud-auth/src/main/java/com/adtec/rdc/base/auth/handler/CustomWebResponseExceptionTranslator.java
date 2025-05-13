package com.adtec.rdc.base.auth.handler;

import com.adtec.rdc.base.auth.exception.CustomOauth2Exception;
import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.enums.OperationStatusEnum;
import com.adtec.rdc.base.common.enums.SysLogTypeEnum;
import com.adtec.rdc.base.common.model.bo.SysOperlog;
import com.adtec.rdc.base.common.util.UrlUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: JTao
 * @date: 2018/10/12 10:25
 * @description: oauth2异常处理器
 */
@Component("customWebResponseExceptionTranslator")
@Slf4j
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Autowired
    private MessageQueueService messageQueueService;

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String username = request.getParameter("username");
        SysOperlog operlog = new SysOperlog();
        operlog
                .setCreateBy(username)
                .setRequestUri(request.getRequestURI())
                .setUserAgent(UrlUtil.getRemoteHost(request))
                .setLogType(SysLogTypeEnum.LOGIN.getCode())
                .setLogStatus(OperationStatusEnum.FAIL.getCode())
                .setModuleName("auth认证模块")
                .setActionName("登录")
                .setServiceId(ServiceNameConstants.BASE_CLOUD_AUTH)
                .setRemoteAddr(UrlUtil.getRemoteHost(request))
                .setMethodName(request.getMethod())
                .setExceptionStr(UrlUtil.getTrace(e));
        messageQueueService.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, operlog);
        log.error(e.getStackTrace().toString());
        if (!(e instanceof OAuth2Exception)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new CustomOauth2Exception(e.getMessage()));
        }
        OAuth2Exception oAuth2Exception = (OAuth2Exception) e;
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomOauth2Exception(oAuth2Exception.getMessage()));
    }
}
