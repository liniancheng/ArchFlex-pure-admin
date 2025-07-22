package com.littlelee.base.common.base.service.feign;

import java.util.concurrent.Future;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;

@FeignClient(name = "base-cloud-message-service", contextId = "base-cloud-common-base-message")
public interface BaseMessageService {
	@PostMapping("/message/sendMail")
	Future<Boolean> sendMail(@RequestBody EmailMessageTemplate emailMessageTemplate);
	@PostMapping("/message/sendSms")
	Future<Boolean> sendSms(@RequestBody SmsMessageTemplate smsMessageTemplate);
}
