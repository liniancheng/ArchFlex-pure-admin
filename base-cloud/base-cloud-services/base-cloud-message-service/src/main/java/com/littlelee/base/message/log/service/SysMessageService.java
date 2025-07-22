package com.littlelee.base.message.log.service;

import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;

public interface SysMessageService {
	Boolean sendMail(EmailMessageTemplate messageTemplate);
	Boolean sendSms(SmsMessageTemplate messageTemplate);
}
