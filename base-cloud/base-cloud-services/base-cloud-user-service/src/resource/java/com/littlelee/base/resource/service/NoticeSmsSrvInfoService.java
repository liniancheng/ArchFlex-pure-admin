package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeSmsSrvInfo;
import com.littlelee.base.resource.model.query.NoticeSmsSrvInfoQuery;

/**
 * @author littlelee
 * @date 2020-06-16 13:00:26
 */
public interface NoticeSmsSrvInfoService extends BaseService<NoticeSmsSrvInfo> {
	NoticeSmsSrvInfoQuery pageByQuery(NoticeSmsSrvInfoQuery query);
}
