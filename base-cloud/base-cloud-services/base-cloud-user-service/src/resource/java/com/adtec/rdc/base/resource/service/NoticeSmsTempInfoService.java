package com.adtec.rdc.base.resource.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeSmsTempInfo;
import com.adtec.rdc.base.resource.model.query.NoticeSmsTempInfoQuery;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
public interface NoticeSmsTempInfoService extends BaseService<NoticeSmsTempInfo> {
	NoticeSmsTempInfoQuery pageByQuery(NoticeSmsTempInfoQuery query);
}
