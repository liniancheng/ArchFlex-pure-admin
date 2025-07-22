package com.littlelee.base.gateway.auth.handler;

import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.exception.PermissionDefinedException;
import com.littlelee.base.common.util.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 授权失败（已登录但权限不足）返回 403
 */
@Slf4j
@Component("reactiveCustomAccessDeniedHandler")
@RequiredArgsConstructor
public class ReactiveCustomAccessDeniedHandler implements ServerAccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException ex) {
        return Mono.defer(() -> {
            try {
                log.info("授权失败, 禁止访问 {}", exchange.getRequest().getURI());

                ApiResult<String> result = new ApiResult<>(new PermissionDefinedException(), ResponseCodeEnum.PERMISSION_DEFINED);
                byte[] bytes = objectMapper.writeValueAsBytes(result);

                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
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