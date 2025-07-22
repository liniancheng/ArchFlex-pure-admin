package com.littlelee.base.gateway.auth.endpoint;

import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.util.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证失败（token 不合法/过期）返回 401
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveAuthExceptionEntryPoint implements ServerAuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        return Mono.defer(() -> {
            try {
                ApiResult<String> result = new ApiResult<>(ex, ResponseCodeEnum.PERMISSION_DEFINED);
                byte[] bytes = objectMapper.writeValueAsBytes(result);

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return exchange.getResponse().writeWith(Mono.just(buffer));
            } catch (Exception e) {
                log.error("write response error", e);
                return Mono.error(e);
            }
        });
    }
}