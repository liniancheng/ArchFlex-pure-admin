package com.adtec.rdc.base.common.aop;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.enums.OperationStatusEnum;
import com.adtec.rdc.base.common.model.bo.SysOperlog;
import com.adtec.rdc.base.common.util.RequestReadUtils;
import com.adtec.rdc.base.common.util.UrlUtil;
import com.adtec.rdc.base.common.util.UserUtil;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author: JTao
 * @date: 2018/11/14 11:26
 */
@Aspect
@Component
@Slf4j
public class SysOperlogAspect {

    @Autowired
    private MessageQueueService messageQueueService;

    @Around("execution(public com.adtec.rdc.base.common.util.ApiResult *(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();

        long startTime = System.currentTimeMillis();
        SysOperlog operlog = new SysOperlog();
        // 需要记录日志存库
        if(targetMethod.isAnnotationPresent(SysLog.class)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            // 获取注解
            SysLog sysLog = targetMethod.getAnnotation(SysLog.class);
            operlog
                    .setServiceId(sysLog.serviceId())
                    .setModuleName(sysLog.moduleName())
                    .setActionName(sysLog.actionName())
                    .setParams(RequestReadUtils.getRequestParams(request, pjp))
                    .setRemoteAddr(UrlUtil.getRemoteHost(request))
                    .setMethodName(request.getMethod())
                    .setRequestUri(request.getRequestURI())
                    .setUserAgent(request.getHeader("user-agent"));
            /*处理特殊url*/
            //1. 修改密码url
            if ( request.getRequestURI().indexOf("/modifyLoginPwd/") > 0) {
            	int index = request.getRequestURI().indexOf("/modifyLoginPwd/");
            	operlog.setRequestUri(request.getRequestURI().substring(0, index + 16));
            }
            //2. 忘记密码
            if ( request.getRequestURI().endsWith("/forgot/forgetPassword")) {
            	operlog.setParams(null);
            }
            /*处理特殊url end*/

            // 获取当前用户名
            String username = UserUtil.getLoginName(request);
            operlog.setCreateBy(username);
        }
        try {
            result = pjp.proceed();
            operlog.setLogStatus(OperationStatusEnum.SUCCESS.getCode());
        } catch (Throwable e) {
        	operlog.setExceptionStr(UrlUtil.getTrace(e));
        	operlog.setLogStatus(OperationStatusEnum.FAIL.getCode());
        	throw e;
        } finally {
            // 本次操作用时（毫秒）
            long elapsedTime = System.currentTimeMillis() - startTime;
            log.info("[{}]use time: {}", pjp.getSignature(), elapsedTime);
            operlog.setOperTime(String.valueOf(elapsedTime));

            // 发送消息到 系统日志队列
            if(targetMethod.isAnnotationPresent(SysLog.class)) {
            	messageQueueService.convertAndSend(MqQueueNameConstant.SYS_LOG_QUEUE, operlog);
            }
        }

        return result;
    }

}
