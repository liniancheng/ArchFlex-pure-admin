package com.littlelee.base.resource.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.resource.model.po.NoticeMailTempInfo;
import com.littlelee.base.resource.model.query.NoticeMailTempInfoQuery;

/**
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
public interface NoticeMailTempInfoService extends BaseService<NoticeMailTempInfo> {
	NoticeMailTempInfoQuery pageByQuery(NoticeMailTempInfoQuery query);
	public List<NoticeMailTempInfo> listTemp(String appId);
}
