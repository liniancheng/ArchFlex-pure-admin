package com.adtec.rdc.base.user.listener;

import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.model.bo.SysOperlog;
import com.adtec.rdc.base.user.model.po.SysOperlogInfo;
import com.adtec.rdc.base.user.service.SysOperlogInfoService;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 监听日志存储请求
 * @author: JTao
 * @date: 2018/11/14 15:24
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "components.messageQueue", havingValue = "true")
@RabbitListener(queues = MqQueueNameConstant.SYS_LOG_QUEUE)
public class SysOperlogReceiveListener {

    @Autowired
    private SysOperlogInfoService sysLogService;

    /**
     * 日志队列消费端，存数据库
     * @param operlog
     * @param channel
     * @param message
     */
    @RabbitHandler
    public void handler(SysOperlog operlog, Channel channel, Message message) throws IOException {
        log.info("系统日志消费端成功消费信息：sysLog={}", operlog);
        // 确认消息接受
        SysOperlogInfo info = new SysOperlogInfo();
        BeanUtils.copyProperties(operlog, info);
        info.setCreateTime(new Date());
        sysLogService.save(info);
    }

}
