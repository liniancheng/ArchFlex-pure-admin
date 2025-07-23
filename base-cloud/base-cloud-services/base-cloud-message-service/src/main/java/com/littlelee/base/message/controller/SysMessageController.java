package com.littlelee.base.message.controller;

import java.util.concurrent.Future;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.common.template.sms.SmsMessageTemplate;
import com.littlelee.base.message.log.service.SysMessageService;


@EnableAsync
@RestController
@RequestMapping("/message")
@Tag(name = "消息发送接口", description = "消息controller")
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
