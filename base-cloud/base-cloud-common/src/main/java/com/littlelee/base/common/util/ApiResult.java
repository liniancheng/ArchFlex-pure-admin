package com.littlelee.base.common.util;

import lombok.Data;

import java.io.Serializable;

import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.exception.ServiceException;

/**
 * @author: littlelee
 * @date: 2018/10/12 10:39
 * @description: 统一响应信息主体
 */
@Data
public class ApiResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private T data;

    private Integer code = ResponseCodeEnum.SUCCESS.getCode();

    private String message = ResponseCodeEnum.SUCCESS.getMessage();

    public ApiResult() {
    }

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResult(T data, ResponseCodeEnum responseCode) {
        this.data = data;
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }

    public ApiResult(Throwable throwable) {
        this.message = throwable.getMessage();
        this.code = ResponseCodeEnum.FAIL.getCode();
    }

    public ApiResult(Throwable throwable, ResponseCodeEnum  code) {
        this.message = throwable.getMessage();
        this.code = code.getCode();
    }
    
    public static ApiResult success(String message) {
    	ApiResult result = new ApiResult();
    	result.message = message==null?"":message;
        result.code = ResponseCodeEnum.SUCCESS.getCode();
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(ResponseCodeEnum.SUCCESS.getCode());
        result.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static ApiResult failed(String message) {
        ApiResult result = new ApiResult();
        result.message = message;
        result.code = ResponseCodeEnum.FAIL.getCode();
        return result;
    }
    public static ApiResult failed(ServiceException e) {
        ApiResult result = new ApiResult();
        result.message = e.getMessage();
        if(e.getErrorCode() != null) {
        	result.code = e.getErrorCode();
        }else {
        	result.code = ResponseCodeEnum.FAIL.getCode();
        }
        return result;
    }

}
