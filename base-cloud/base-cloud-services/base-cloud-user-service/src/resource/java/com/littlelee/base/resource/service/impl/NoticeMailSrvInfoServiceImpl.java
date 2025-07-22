package com.littlelee.base.resource.service.impl;

import com.littlelee.base.common.base.service.MessageQueueService;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.constants.MqQueueNameConstant;
import com.littlelee.base.common.enums.EmailMessageChannnelEnum;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.email.MailSendBean;
import com.littlelee.base.common.model.bo.email.MailServerBean;
import com.littlelee.base.common.template.email.EmailMessageTemplate;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.resource.mapper.NoticeMailSrvInfoMapper;
import com.littlelee.base.resource.mapper.NoticeMailTempInfoMapper;
import com.littlelee.base.resource.model.bo.MailSendTestBean;
import com.littlelee.base.resource.model.po.NoticeMailSrvInfo;
import com.littlelee.base.resource.model.po.NoticeMailTempInfo;
import com.littlelee.base.resource.model.query.NoticeMailSrvInfoQuery;
import com.littlelee.base.resource.service.NoticeMailSrvInfoService;
import com.littlelee.base.resource.utils.MailUtils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
@Service
public class NoticeMailSrvInfoServiceImpl extends BaseServiceImpl<NoticeMailSrvInfoMapper, NoticeMailSrvInfo> implements NoticeMailSrvInfoService {
	@Autowired
    private NoticeMailSrvInfoMapper mapper;
	@Autowired
    private NoticeMailTempInfoMapper tempMapper;
	@Autowired
	private MessageQueueService messageQueueService;
	
	@Override
	public NoticeMailSrvInfoQuery pageByQuery(NoticeMailSrvInfoQuery query) {
		query.addOrder(OrderItem.desc("CREATE_TIME"))
				.addOrder(OrderItem.desc("MODIFY_TIME"));
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	public boolean save(NoticeMailSrvInfo entity) {
		// 判断服务器名称
		if (mapper.isSameSrvName(entity.getSrvName(),entity.getAppId()) > 0) {
			throw new ServiceException(ErrorCodeEnum.SRV_RE_NAME.getErrorCode(),
					ErrorCodeEnum.SRV_RE_NAME.getMessage());
		}
		return super.save(entity);
	}
	
	@Override
	public boolean updateById(NoticeMailSrvInfo entity) {
		// 判断服务器名称
		if (mapper.isExistSrvTitle(entity)) {
			throw new ServiceException(ErrorCodeEnum.SRV_RE_NAME.getErrorCode(),
					ErrorCodeEnum.SRV_RE_NAME.getMessage());
		}
		return super.updateById(entity);
	}
	
	@Override
	public String test(MailSendTestBean sendTest) {
		NoticeMailSrvInfo mailSrvInfo = mapper.selectById(sendTest.getSrvId());
		NoticeMailTempInfo mailTempInfo = tempMapper.selectById(sendTest.getTempId());
		MailServerBean mailServer = MailUtils.getMailServer(mailSrvInfo);
		MailSendBean mailSend = MailUtils.getMailSend(sendTest, mailTempInfo);
		EmailMessageTemplate emailMessageTemplate = new EmailMessageTemplate();
		emailMessageTemplate.setMailServer(mailServer);
		emailMessageTemplate.setMailSend(mailSend);
		emailMessageTemplate.setChannel(EmailMessageChannnelEnum.JAVA_MAIL.getCode());
		messageQueueService.convertAndSend(MqQueueNameConstant.MAIL_CODE_QUEUE, emailMessageTemplate);
		return "邮件已发送";
	}

}
