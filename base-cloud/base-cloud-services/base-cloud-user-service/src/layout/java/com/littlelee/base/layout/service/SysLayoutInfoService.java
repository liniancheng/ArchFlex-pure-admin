package com.littlelee.base.layout.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.layout.model.po.SysLayoutInfo;
import com.littlelee.base.layout.model.query.SysLayoutInfoQuery;

/**
 * @author littlelee
 * @date 2020-08-25 15:04:09
 */
public interface SysLayoutInfoService extends BaseService<SysLayoutInfo> {
	SysLayoutInfoQuery pageByQuery(SysLayoutInfoQuery query);
	
	boolean isExistName(SysLayoutInfo entity);
	
	boolean isExistLevel(SysLayoutInfo entity);
	
	SysLayoutInfo fetchOnce(String id, String appId);
	/**
	 * 按登录名查询首页布局
	 */
	SysLayoutInfo fetchByLoginName(String loginName, String appId);

	SysLayoutInfo fetchLayout(String loginName, String appId, List<String> roleIds);
}
 