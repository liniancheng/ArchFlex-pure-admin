package com.littlelee.base.common.exception;

import com.littlelee.base.common.model.bo.ErrorEnum;

/**
 * @author: littlelee
 * @date: 2018/12/3 09:19
 */
public class ServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Integer errorCode;
	
	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public ServiceException() {
    }

	public ServiceException(String message) {
        super(message);
    }
	public ServiceException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
	public ServiceException(ErrorEnum e) {
        super(e.getMessage());
        this.errorCode = e.getErrorCode();
    }
}
