package com.adtec.rdc.base.auth.exception;

import com.adtec.rdc.base.auth.serializer.CustomOauthExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author: JTao
 * @date: 2018/10/12 10:18
 * @description: oauth2自定义异常
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

	private static final long serialVersionUID = 1L;

	public CustomOauth2Exception(String msg) {
        super(msg);
    }
}
