package com.adtec.rdc.base.anno.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.common.exception.ServiceException;

import java.util.List;

import com.adtec.rdc.base.anno.model.po.SysAnnoTypeInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoTypeInfoQuery;

/**
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
public interface SysAnnoTypeInfoService extends BaseService<SysAnnoTypeInfo> {
	SysAnnoTypeInfoQuery pageByQuery(SysAnnoTypeInfoQuery query);
	
	boolean isExistTypeName(SysAnnoTypeInfo query);
	
	boolean onlySave(SysAnnoTypeInfo annoType) throws ServiceException;

	boolean onlyUpdate(SysAnnoTypeInfo annoType) throws ServiceException;
	
	List<SysAnnoTypeInfo> findAll(String appId);
}
