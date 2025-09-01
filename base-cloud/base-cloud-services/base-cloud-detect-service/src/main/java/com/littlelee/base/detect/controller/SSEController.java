package com.littlelee.base.detect.controller;


import com.littlelee.base.detect.util.SSEMsgType;
import com.littlelee.base.detect.util.SSEServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("sse")
public class SSEController {

    /**
     * @Description: 连接sse服务的接口
     * @Author littlelee
     * @param userId
     * @return SseEmitter
     */
    @GetMapping(path = "connect", produces = {MediaType.TEXT_EVENT_STREAM_VALUE})
    public SseEmitter connect(@RequestParam String userId) {
        return SSEServer.connect(userId);
    }

    /**
     * @Description: 发送单条消息给SSE的客户端
     * @Author littlelee
     * @param userId
     * @param message
     * @return Object
     */
    @GetMapping("sendMessage")
    public Object sendMessage(@RequestParam String userId, @RequestParam String message) {
        SSEServer.sendMessage(userId, message, SSEMsgType.MESSAGE);
        return "OK";
    }

    /**
     * @Description: 发送消息给所有客户端用户
     * @Author littlelee
     * @param message
     * @return Object
     */
    @GetMapping("sendMessageAll")
    public Object sendMessageAll(@RequestParam String message) {
        SSEServer.sendMessageToAllUsers(message);
        return "OK";
    }

    /**
     * @Description: add事件流式输出
     * @Author littlelee
     * @param userId
     * @param message
     * @return Object
     */
    @GetMapping("sendMessageAdd")
    public Object sendMessageAdd(@RequestParam String userId,
                                 @RequestParam String message) throws Exception {
        for (int i = 0 ; i < 10 ; i ++) {
            Thread.sleep(200);
            SSEServer.sendMessage(userId, message + "-" + i, SSEMsgType.ADD);
        }
        return "OK";
    }

    /**
     * @Description: 停止sse
     * @Author littlelee
     * @param userId
     * @return Object
     */
    @GetMapping("stop")
    public Object stopServer(@RequestParam String userId)  {
        SSEServer.stopServer(userId);
        return "OK";
    }

    /**
     * @Description: 获得当前所有的会话总连接数（在线人数）
     * @Author littlelee
     * @param
     * @return Object
     */
    @GetMapping("getOnlineCounts")
    public Object getOnlineCounts() {
        return SSEServer.getOnlineCounts();
    }

}
