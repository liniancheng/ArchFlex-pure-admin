package com.littlelee.base.auth.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * Spring Authorization Server 6.x 自定义异常
 *
 * @author: littlelee
 * @date: 2018/10/12 10:18
 */
public class CustomOAuth2Exception extends OAuth2AuthenticationException {

    public CustomOAuth2Exception(String errorCode, String description) {
        this(errorCode, description, null);
    }

    public CustomOAuth2Exception(String errorCode, String description, String uri) {
        super(new OAuth2Error(errorCode, description, uri));
    }
}