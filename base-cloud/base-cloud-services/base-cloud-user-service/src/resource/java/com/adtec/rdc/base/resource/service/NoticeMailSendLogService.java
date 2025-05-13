package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeMailSendLog;
import com.adtec.rdc.base.resource.model.query.NoticeMailSendLogQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
public interface NoticeMailSendLogService extends BaseService<NoticeMailSendLog> {
	NoticeMailSendLogQuery pageByQuery(NoticeMailSendLogQuery query);
}
