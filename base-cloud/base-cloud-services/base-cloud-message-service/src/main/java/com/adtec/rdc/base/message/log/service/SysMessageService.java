package com.adtec.rdc.base.message.log.service;

import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;

public interface SysMessageService {
	Boolean sendMail(EmailMessageTemplate messageTemplate);
	Boolean sendSms(SmsMessageTemplate messageTemplate);
}
