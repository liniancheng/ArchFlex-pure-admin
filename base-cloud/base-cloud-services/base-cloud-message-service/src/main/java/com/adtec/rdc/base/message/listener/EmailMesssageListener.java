package com.adtec.rdc.base.message.listener;

import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.enums.EmailMessageChannnelEnum;
import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.message.handler.email.EmailMessageHandler;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author: JTao
 * @date: 2018/12/3 10:56
 */
@Slf4j
@Component
@RabbitListener(queues = MqQueueNameConstant.MAIL_CODE_QUEUE)
@ConditionalOnProperty(value = "components.messageQueue", havingValue = "true")
public class EmailMesssageListener {

    @Autowired
    private Map<String, EmailMessageHandler> emailMessageHandlerMap;

    @RabbitHandler
    public void listener(EmailMessageTemplate messageTemplate) {
        long start = System.currentTimeMillis();
        log.info("message service had receive push request");
        String channel = messageTemplate.getChannel();
        if(StringUtils.isEmpty(channel)) {
        	channel = EmailMessageChannnelEnum.JAVA_MAIL.getCode();
        }
        EmailMessageHandler emailMessageHandler = emailMessageHandlerMap.get(channel);
        if(Objects.isNull(emailMessageHandler)) {
            log.error("not found channel:{}.", channel);
        }
        emailMessageHandler.handleMessage(messageTemplate);
        long end = System.currentTimeMillis();
        log.info("had send message, use time:{} ms", (end -start));
    }

}
