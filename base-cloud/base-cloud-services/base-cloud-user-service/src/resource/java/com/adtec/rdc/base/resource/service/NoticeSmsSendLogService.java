package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeSmsSendLog;
import com.adtec.rdc.base.resource.model.query.NoticeSmsSendLogQuery;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
public interface NoticeSmsSendLogService extends BaseService<NoticeSmsSendLog> {
	NoticeSmsSendLogQuery pageByQuery(NoticeSmsSendLogQuery query);
}
