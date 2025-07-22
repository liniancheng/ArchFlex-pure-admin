package com.littlelee.base.message.log.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.enums.EmailMessageChannnelEnum;
import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;
import com.littlelee.base.message.handler.email.EmailMessageHandler;
import com.littlelee.base.message.handler.sms.SmsMessageHandler;
import com.littlelee.base.message.log.service.SysMessageService;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SysMessageServiceImpl implements SysMessageService {
	@Autowired
    private Map<String, EmailMessageHandler> emailMessageHandlerMap;
	@Autowired
    private Map<String, SmsMessageHandler> smsMessageHandlerMap;
	
	@Override
	public Boolean sendMail(EmailMessageTemplate messageTemplate) {
		String channel = messageTemplate.getChannel();
        if(StringUtils.isEmpty(channel)) {
        	channel = EmailMessageChannnelEnum.JAVA_MAIL.getCode();
        }
        EmailMessageHandler emailMessageHandler = emailMessageHandlerMap.get(channel);
        if(Objects.isNull(emailMessageHandler)) {
            log.error("not found channel:{}.", channel);
        }
        emailMessageHandler.handleMessage(messageTemplate);
		return Boolean.TRUE;
	}

	@Override
	public Boolean sendSms(SmsMessageTemplate messageTemplate) {
		String channel = messageTemplate.getChannel();
        SmsMessageHandler smsMessageHandler = smsMessageHandlerMap.get(channel);
        if(Objects.isNull(smsMessageHandler)) {
            log.error("not found channel:{}.", channel);
        }
        smsMessageHandler.handleMessage(messageTemplate);
		return Boolean.TRUE;
	}

}
