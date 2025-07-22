package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeSmsSendLog;
import com.littlelee.base.resource.model.query.NoticeSmsSendLogQuery;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
public interface NoticeSmsSendLogService extends BaseService<NoticeSmsSendLog> {
	NoticeSmsSendLogQuery pageByQuery(NoticeSmsSendLogQuery query);
}
