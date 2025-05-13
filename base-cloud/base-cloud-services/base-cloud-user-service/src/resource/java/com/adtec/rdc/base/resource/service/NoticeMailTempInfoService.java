package com.adtec.rdc.base.resource.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;
import com.adtec.rdc.base.resource.model.query.NoticeMailTempInfoQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
public interface NoticeMailTempInfoService extends BaseService<NoticeMailTempInfo> {
	NoticeMailTempInfoQuery pageByQuery(NoticeMailTempInfoQuery query);
	public List<NoticeMailTempInfo> listTemp(String appId);
}
