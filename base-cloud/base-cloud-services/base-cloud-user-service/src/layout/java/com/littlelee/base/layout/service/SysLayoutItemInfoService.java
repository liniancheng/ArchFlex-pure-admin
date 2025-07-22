package com.littlelee.base.layout.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.layout.model.po.SysLayoutItemInfo;
import com.littlelee.base.layout.model.query.SysLayoutItemInfoQuery;

/**
 * @author littlelee
 * @date 2020-08-25 15:17:06
 */
public interface SysLayoutItemInfoService extends BaseService<SysLayoutItemInfo> {
	SysLayoutItemInfoQuery pageByQuery(SysLayoutItemInfoQuery query);
	
	boolean isExist(SysLayoutItemInfo entity);

	SysLayoutItemInfoQuery fetchPerson(SysLayoutItemInfoQuery query, List<String> list);
}
