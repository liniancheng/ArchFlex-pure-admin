package com.littlelee.base.gateway.router;

import com.littlelee.base.gateway.entity.SysZuulRoute;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

/**
 * Gateway 数据库动态路由（取代 Zuul 的 DynamicRouteLocator）
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayDbRouteDefinitionRepository implements RouteDefinitionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 加载全部路由定义
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        List<SysZuulRoute> dbRoutes = jdbcTemplate.query(
                "SELECT * FROM sys_zuul_route WHERE del_flag = 0",
                new BeanPropertyRowMapper<>(SysZuulRoute.class));

        List<RouteDefinition> definitions = new ArrayList<>();
        for (SysZuulRoute r : dbRoutes) {
            try {
                definitions.add(buildRouteDefinition(r));
            } catch (Exception e) {
                log.error("解析路由配置异常: {}", r, e);
            }
        }
        return Flux.fromIterable(definitions);
    }

    private RouteDefinition buildRouteDefinition(SysZuulRoute r) {
        RouteDefinition rd = new RouteDefinition();
        rd.setId(r.getServiceId());
        rd.setUri(parseUri(r.getUrlProxy(), r.getServiceId()));
        rd.setOrder(0);

        /* Path 断言 */
        PredicateDefinition pathDef = new PredicateDefinition();
        pathDef.setName("Path");
        pathDef.addArg("_genkey_0", r.getRoutePath());
        rd.setPredicates(Collections.singletonList(pathDef));

        /* 过滤器列表 */
        List<FilterDefinition> filters = new ArrayList<>();

        // 如果stripPrefix字段不等于"0"，则会添加一个StripPrefix过滤器，剥离路径中的第一个路径部分（默认为1）。
        boolean stripPrefix = !Objects.equals("0", r.getStripPrefix());
        if (stripPrefix) {
            FilterDefinition strip = new FilterDefinition();
            strip.setName("StripPrefix");
            strip.addArg("_genkey_0", "1");
            filters.add(strip);
        }

        /* Retry */
        boolean retryable = !Objects.equals("0", r.getRetryableFlag());
        if (retryable) {
            FilterDefinition retry = new FilterDefinition();
            retry.setName("Retry");
            retry.addArg("retries", "3");
            filters.add(retry);
        }

        /* Sensitive Headers Remove */
        if (r.getHeadersList() != null && !r.getHeadersList().isBlank()) {
            FilterDefinition remove = new FilterDefinition();
            remove.setName("RemoveRequestHeader");
            Arrays.stream(r.getHeadersList().split(","))
                    .map(String::trim)
                    .forEach(h -> {
                        FilterDefinition f = new FilterDefinition();
                        f.setName("RemoveRequestHeader");
                        f.addArg("_genkey_0", h);
                        filters.add(f);
                    });
        }

        rd.setFilters(filters);
        log.debug("加载数据库路由: id={}, path={}, uri={}, stripPrefix={}, retryable={}, headers={}",
                rd.getId(), r.getRoutePath(), rd.getUri(), stripPrefix, retryable, r.getHeadersList());
        return rd;
    }

    private URI parseUri(String urlProxy, String serviceId) {
        if (urlProxy == null || urlProxy.isBlank()) {
            // lb://serviceId 形式
            return URI.create("lb://" + serviceId);
        }
        // http/https 直连
        return URI.create(urlProxy);
    }

    /* 数据库模式，无需持久化/删除，留空即可 */
    @Override public Mono<Void> save(Mono<RouteDefinition> route) { return Mono.empty(); }
    @Override public Mono<Void> delete(Mono<String> routeId) { return Mono.empty(); }
}