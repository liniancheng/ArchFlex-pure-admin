package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeMailSendLog;
import com.littlelee.base.resource.model.query.NoticeMailSendLogQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:30:22
 */
public interface NoticeMailSendLogService extends BaseService<NoticeMailSendLog> {
	NoticeMailSendLogQuery pageByQuery(NoticeMailSendLogQuery query);
}
