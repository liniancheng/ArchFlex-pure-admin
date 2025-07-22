package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.bo.MailSendTestBean;
import com.littlelee.base.resource.model.po.NoticeMailSrvInfo;
import com.littlelee.base.resource.model.query.NoticeMailSrvInfoQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
public interface NoticeMailSrvInfoService extends BaseService<NoticeMailSrvInfo> {
	NoticeMailSrvInfoQuery pageByQuery(NoticeMailSrvInfoQuery query);
	String test(MailSendTestBean sendTest);
}
