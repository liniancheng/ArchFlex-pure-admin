package com.adtec.rdc.base.message.log.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.enums.EmailMessageChannnelEnum;
import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;
import com.adtec.rdc.base.message.handler.email.EmailMessageHandler;
import com.adtec.rdc.base.message.handler.sms.SmsMessageHandler;
import com.adtec.rdc.base.message.log.service.SysMessageService;

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
