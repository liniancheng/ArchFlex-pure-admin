package com.littlelee.base.detect.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ServerEndpoint("/ws/camera/detect")
@Component
public class CameraWebSocket {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);
    private static PythonDetectService pythonService = new PythonDetectService();

    @OnOpen
    public void onOpen(Session session) {
        log.info("WebSocket连接建立: {}", session.getId());
    }

    @OnMessage
    public void onMessage(String base64Image, Session session) {
        EXECUTOR.submit(() -> {
            try {
                // 解码图像、传递给 Python 脚本处理
                String resultBase64 = pythonService.detectFromBase64(base64Image);
                // 发送处理后的图像回前端
                session.getBasicRemote().sendText(resultBase64);
            } catch (Exception e) {
                log.error("处理检测失败", e);
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        log.info("WebSocket连接关闭: {}", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket发生错误: {}", session.getId(), error);
    }
}
