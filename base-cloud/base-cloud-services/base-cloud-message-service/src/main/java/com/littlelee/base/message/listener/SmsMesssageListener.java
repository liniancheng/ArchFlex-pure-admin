package com.littlelee.base.message.listener;

import com.littlelee.base.common.constants.MqQueueNameConstant;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;
import com.littlelee.base.message.handler.sms.SmsMessageHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author: littlelee
 * @date: 2018/12/3 10:56
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueNameConstant.MOBILE_CODE_QUEUE)
@ConditionalOnProperty(value = "components.messageQueue", havingValue = "true")
public class SmsMesssageListener {

    @Autowired
    private Map<String, SmsMessageHandler> smsMessageHandlerMap;

    @RabbitHandler
    public void listener(SmsMessageTemplate messageTemplate) {
        long start = System.currentTimeMillis();
        log.info("message service had receive push request");
        String channel = messageTemplate.getChannel();
        SmsMessageHandler smsMessageHandler = smsMessageHandlerMap.get(channel);
        if(Objects.isNull(smsMessageHandler)) {
            log.error("not found channel:{}.", channel);
        }
        smsMessageHandler.handleMessage(messageTemplate);
        long end = System.currentTimeMillis();
        log.info("had send message, use time:{} ms", (end -start));
    }

}
