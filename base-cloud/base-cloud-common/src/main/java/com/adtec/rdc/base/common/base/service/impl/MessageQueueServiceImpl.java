package com.adtec.rdc.base.common.base.service.impl;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;
import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.base.service.feign.BaseMessageService;
import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.base.service.feign.DgmpCommonService;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.model.bo.StatisticalInfo;
import com.adtec.rdc.base.common.model.bo.SysOperlog;

@Component
public class MessageQueueServiceImpl implements MessageQueueService {
	@Value("${components.messageQueue}")
	private Boolean messageQueue;
	
	@Autowired(required=false)
    private RabbitTemplate rabbitTemplate;
	@Autowired
    private BaseUserService userService;
	@Autowired
    private BaseMessageService messageService;
	@Autowired
    private DgmpCommonService dgmpCommonService;

	@Override
	public void convertAndSend(String queue, Object object) {
		if(messageQueue) {
			rabbitTemplate.convertAndSend(queue, object);	
		}else {
			if(queue.equals(MqQueueNameConstant.SYS_LOG_QUEUE)) {//系统日志队列
				userService.saveOperLog((SysOperlog)object);
			}else if(queue.equals(MqQueueNameConstant.MOBILE_CODE_QUEUE)) {//短信验证码队列
				messageService.sendSms((SmsMessageTemplate)object);
			}else if(queue.equals(MqQueueNameConstant.MAIL_CODE_QUEUE)) {//邮件队列
				messageService.sendMail((EmailMessageTemplate)object);
			}else if(queue.equals(MqQueueNameConstant.REQUEST_STATISTICS_QUEUE)) {//访问统计队列
				userService.saveStatistical((StatisticalInfo)object);
			}else if(queue.equals(MqQueueNameConstant.REPORT_AUTH_QUEUE)) {//报表权限同步队列
				
			}else if(queue.equals(MqQueueNameConstant.REPORT_USER_QUEUE)) {//报表用户同步队列
				
			}else if(queue.equals(MqQueueNameConstant.REPORT_ROLE_QUEUE)) {//报表角色同步队列
				
			}else if(queue.equals(MqQueueNameConstant.REPORT_BRANCH_QUEUE)) {//报表机构同步队列
				
			}else if(queue.equals(MqQueueNameConstant.REPORT_BRANCH_BATCH_QUEUE)) {//报表机构批量同步队列
				
			}else if(queue.equals(MqQueueNameConstant.REPORT_REQUEST_QUEUE)) {//报表访问统计队列
				
			}else if(queue.equals(MqQueueNameConstant.ELASTICSEARCH_QUEUE)) {//搜索删除队列
				dgmpCommonService.deleteSearchIndex((Map<String, String>)object);
			}
		}		
	}
}
