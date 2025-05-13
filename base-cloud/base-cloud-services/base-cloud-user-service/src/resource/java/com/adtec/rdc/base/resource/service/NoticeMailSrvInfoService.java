package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.bo.MailSendTestBean;
import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;
import com.adtec.rdc.base.resource.model.query.NoticeMailSrvInfoQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
public interface NoticeMailSrvInfoService extends BaseService<NoticeMailSrvInfo> {
	NoticeMailSrvInfoQuery pageByQuery(NoticeMailSrvInfoQuery query);
	String test(MailSendTestBean sendTest);
}
