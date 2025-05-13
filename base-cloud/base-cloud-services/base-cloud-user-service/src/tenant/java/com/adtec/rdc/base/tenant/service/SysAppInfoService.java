package com.adtec.rdc.base.tenant.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.tenant.model.bo.SysAppTree;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
public interface SysAppInfoService extends BaseService<SysAppInfo> {
	
	boolean saveApp(SysAppInfo app, String userId);

	boolean deleteById(String id);
	
	List<SysAppTree> getAllAppTree(SysAppInfo info);

	int getAppRelCount(String id);

	Boolean updateByAppId(SysAppInfo app);

	/**
	 * 获取有权的租户
	 * @param userId
	 * @return
	 */
	List<SysAppTree> getAppAuthByUserId(String userId);
}