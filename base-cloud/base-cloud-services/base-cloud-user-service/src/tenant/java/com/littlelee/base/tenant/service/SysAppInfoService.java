package com.littlelee.base.tenant.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.tenant.model.bo.SysAppTree;
import com.littlelee.base.tenant.model.po.SysAppInfo;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
public interface SysAppInfoService extends BaseService<SysAppInfo> {
	
	boolean saveApp(SysAppInfo app, String userId);

	boolean deleteById(String id);
	
	List<SysAppTree> getAllAppTree(SysAppInfo info);

	long getAppRelCount(String id);

	Boolean updateByAppId(SysAppInfo app);

	/**
	 * 获取有权的租户
	 * @param userId
	 * @return
	 */
	List<SysAppTree> getAppAuthByUserId(String userId);
}