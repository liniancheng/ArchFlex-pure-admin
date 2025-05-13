package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeSmsSrvInfo;
import com.adtec.rdc.base.resource.model.query.NoticeSmsSrvInfoQuery;

/**
 * @author adtec
 * @date 2020-06-16 13:00:26
 */
public interface NoticeSmsSrvInfoService extends BaseService<NoticeSmsSrvInfo> {
	NoticeSmsSrvInfoQuery pageByQuery(NoticeSmsSrvInfoQuery query);
}
