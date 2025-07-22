package com.littlelee.base.resource.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeSmsTempInfo;
import com.littlelee.base.resource.model.query.NoticeSmsTempInfoQuery;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
public interface NoticeSmsTempInfoService extends BaseService<NoticeSmsTempInfo> {
	NoticeSmsTempInfoQuery pageByQuery(NoticeSmsTempInfoQuery query);
}
