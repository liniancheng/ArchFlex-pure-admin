package com.littlelee.base.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 打印原始请求路径
        System.out.println("Original request path: " + exchange.getRequest().getPath());

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // 获取路由后的目标地址
            URI targetUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
            System.out.println("Forwarded to: " + targetUri);

            // 打印响应状态码
            System.out.println("Response status code: " + exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        // 确保在StripPrefix等过滤器之前或之后执行，根据需要调整优先级
        return -1;
    }
}
