package com.littlelee.base.auth.mobile;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationGrantAuthenticationToken;

import java.util.Map;
import java.util.Set;

/**
 * 仅用于 Spring Authorization Server 6.x 的自定义 grant_type。
 */
public class MobileGrantAuthenticationToken extends OAuth2AuthorizationGrantAuthenticationToken {

    private final String mobile;
    private final String code;

    public MobileGrantAuthenticationToken(String mobile,
                                          String code,
                                          Authentication clientPrincipal,
                                          Set<String> scopes,
                                          Map<String, Object> additionalParameters) {
        // 3 个参数：grantType, clientPrincipal, additionalParameters
        super(new AuthorizationGrantType("mobile"), clientPrincipal, additionalParameters);
        this.mobile = mobile;
        this.code = code;
    }

    public String getMobile() { return mobile; }
    public String getCode()   { return code; }
}