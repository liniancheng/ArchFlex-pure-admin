package com.adtec.rdc.base.tenant.mapper;

import java.util.List;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
public interface SysAppInfoMapper extends BaseMapper<SysAppInfo> {
	
	/**
	 * 获取有权限的租户信息
	 * 	
	 * @param userId 
	 * @param appId
	 * @param appState
	 * @return
	 */
	List<SysAppInfo> getAppsByUserId(String userId, String appId, String appState);
	
	Integer updateStateById(List<SysAppInfo> appList);
	
	/**
	 * 判断数据库中是否含有相同名称租户
	 * @param app
	 * @return
	 */
	Integer getCountByName(String appName);
	
	/**
	 * 获取关联租户
	 * @param appId
	 * @return
	 */
	List<SysAppInfo> getAppsByAppId(String appId);

}
