package com.littlelee.base.auth.mobile;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Map;
import java.util.Set;

public class MobileAuthenticationConverter implements AuthenticationConverter {

    @Override
    public Authentication convert(HttpServletRequest request) {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!MobileGrant.GRANT_TYPE.equals(grantType)) return null;

        String mobile = request.getParameter("mobile");
        String code   = request.getParameter("code");
        String scope  = request.getParameter(OAuth2ParameterNames.SCOPE);

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
        Set<String> scopes = scope == null ? Set.of() : Set.of(scope.split(" "));

        return new MobileGrantAuthenticationToken(
                mobile, code, clientPrincipal, scopes, Map.of());
    }
}