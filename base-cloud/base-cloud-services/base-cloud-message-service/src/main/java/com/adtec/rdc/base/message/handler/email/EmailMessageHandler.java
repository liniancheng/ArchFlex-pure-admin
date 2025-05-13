package com.adtec.rdc.base.message.handler.email;

import org.apache.commons.lang.StringUtils;

import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.template.MessageTemplate;
import com.adtec.rdc.base.common.template.email.EmailMessageTemplate;
import com.adtec.rdc.base.message.handler.AbstractMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: JTao
 * @date: 2018/12/3 09:34
 */
@Slf4j
public abstract class EmailMessageHandler extends AbstractMessageHandler {

	@Override
	public void check(MessageTemplate messageTemplate) {
		//crossvalidation type
		if (!(messageTemplate instanceof EmailMessageTemplate)) {
            throw new ServiceException("messageTemplate must instance of EmailMessageTemplate");
        }
		//crossvalidation server
		EmailMessageTemplate emailMessageTemplate = (EmailMessageTemplate) messageTemplate;
		if(emailMessageTemplate.getMailServer() == null) {
            throw new ServiceException("mail server must not be null");
        }
		//crossvalidation send
		if(emailMessageTemplate.getMailSend() == null) {
            throw new ServiceException("mail send must not be null");
        }
		if(StringUtils.isEmpty(emailMessageTemplate.getMailSend().getTitle())) {
            throw new ServiceException("mail title must not be null");
        }
		if(null == emailMessageTemplate.getMailSend().getToMails() || emailMessageTemplate.getMailSend().getToMails().size() == 0) {
            throw new ServiceException("mail to address must not be null");
        }
	}

	@Override
	public abstract Boolean execute(MessageTemplate messageTemplate);

	@Override
	public void fail(MessageTemplate messageTemplate) {
		EmailMessageTemplate emailMessageTemplate = (EmailMessageTemplate) messageTemplate;
		log.error("邮件发送失败，服务器:{}，邮件：{}", emailMessageTemplate.getMailServer().getSrvName(),
				emailMessageTemplate.getMailSend().getTitle());
	}
}
