package com.littlelee.base.gateway.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Gateway 路由刷新事件发布（Spring Cloud Gateway 版）
 *
 * @author littlelee
 */
@Service
public class RefreshRouteService {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 发布刷新路由事件，Gateway 会自动重新加载 RouteDefinition
     */
    public void refreshRoute() {
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}