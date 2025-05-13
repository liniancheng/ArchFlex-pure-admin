package com.adtec.rdc.base.message.controller;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;
import com.adtec.rdc.base.message.log.service.SysMessageService;

import io.swagger.annotations.Api;

@EnableAsync
@RestController
@RequestMapping("/message")
@Api(value = "消息controller", tags = { "消息发送接口" })
public class SysMessageController {
	@Autowired
	private SysMessageService service;
	
	@PostMapping("/sendMail")
    public Future<Boolean> sendMail(@RequestBody EmailMessageTemplate messageTemplate){
        return new AsyncResult<>(service.sendMail(messageTemplate));
    }
	
	@PostMapping("/sendSms")
    public Future<Boolean> sendSms(@RequestBody SmsMessageTemplate messageTemplate){
        return new AsyncResult<>(service.sendSms(messageTemplate));
    }
}
