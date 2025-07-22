package com.littlelee.base.param.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.param.model.po.SysParamInfo;
import com.littlelee.base.param.query.SysParamInfoQuery;

public interface SysParamInfoService extends BaseService<SysParamInfo>{

	SysParamInfoQuery pageByQuery(SysParamInfoQuery query);
	
	boolean onlySave(SysParamInfo param);
	
	boolean onlyUpdate(SysParamInfo param);
	
	boolean isExistParamName(SysParamInfo param);
	/**
	 * @author littlelee
	 * @param paramName 参数标识
	 * @param expAppId		管理端租户标识
	 * @return
	 */
	String getParam(String paramName, String expAppId);
}
