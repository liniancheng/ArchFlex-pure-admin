package com.adtec.rdc.base.message.handler.sms;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.template.MessageTemplate;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;
import com.adtec.rdc.base.message.handler.AbstractMessageHandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @author: JTao
 * @date: 2018/12/3 09:03
 */
@Slf4j
public abstract class SmsMessageHandler extends AbstractMessageHandler {

	@Override
	public void check(MessageTemplate messageTemplate) {
		if (!(messageTemplate instanceof SmsMessageTemplate)) {
			throw new ServiceException("messageTemplate must instance of SmsMessageTemplate");
		}
		SmsMessageTemplate smsMessageTemplate = (SmsMessageTemplate) messageTemplate;
		if (StringUtils.isEmpty(smsMessageTemplate.getMobile())) {
			throw new ServiceException("mobile must  not be null");
		}
		if (StringUtils.isEmpty(smsMessageTemplate.getSignName())) {
			throw new ServiceException("singName must  not be null");
		}
		if (StringUtils.isEmpty(smsMessageTemplate.getChannel())) {
			throw new ServiceException("channel must  not be null");
		}
		if (StringUtils.isEmpty(smsMessageTemplate.getTemplate())) {
			throw new ServiceException("template must  not be null");
		}
	}

	@Override
	public abstract Boolean execute(MessageTemplate messageTemplate);

	@Override
	public void fail(MessageTemplate messageTemplate) {
		SmsMessageTemplate smsMessageTemplate = (SmsMessageTemplate) messageTemplate;
		log.error("短信发送失败，手机号:{},签名:{},模板:{}", smsMessageTemplate.getMobile(), smsMessageTemplate.getSignName(),
				smsMessageTemplate.getTemplate());
	}
}
