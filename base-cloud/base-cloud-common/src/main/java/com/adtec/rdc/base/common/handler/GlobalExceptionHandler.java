package com.adtec.rdc.base.common.handler;

import com.adtec.rdc.base.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.adtec.rdc.base.common.util.ApiResult;

/**
 * @author: JTao
 * @date: 2018/11/13 16:30
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult serviceException(ServiceException e) {
//        return new ApiResult(e);
    	//若使用全局唯一异常码，注释掉这个判断
    	if (e.getErrorCode() != null) {
    		return new ApiResult().failed(e);
    	}
    	return new ApiResult(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResult exception(Exception e) {
        log.error("发生异常, e={}",e.getMessage(), e);
        return new ApiResult(e);
    }

}
