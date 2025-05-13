package com.adtec.rdc.base.param.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.adtec.rdc.base.param.query.SysParamInfoQuery;

public interface SysParamInfoService extends BaseService<SysParamInfo>{

	SysParamInfoQuery pageByQuery(SysParamInfoQuery query);
	
	boolean onlySave(SysParamInfo param);
	
	boolean onlyUpdate(SysParamInfo param);
	
	boolean isExistParamName(SysParamInfo param);
	/**
	 * @author dengchf
	 * @param paramName 参数标识
	 * @param expAppId		管理端租户标识
	 * @return
	 */
	String getParam(String paramName, String expAppId);
}
