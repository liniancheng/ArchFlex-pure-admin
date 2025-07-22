package com.littlelee.base.anno.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.exception.ServiceException;

import java.util.List;

import com.littlelee.base.anno.model.po.SysAnnoTypeInfo;
import com.littlelee.base.anno.model.query.SysAnnoTypeInfoQuery;

/**
 * @author littlelee
 * @date 2019-11-26 09:51:11
 */
public interface SysAnnoTypeInfoService extends BaseService<SysAnnoTypeInfo> {
	SysAnnoTypeInfoQuery pageByQuery(SysAnnoTypeInfoQuery query);
	
	boolean isExistTypeName(SysAnnoTypeInfo query);
	
	boolean onlySave(SysAnnoTypeInfo annoType) throws ServiceException;

	boolean onlyUpdate(SysAnnoTypeInfo annoType) throws ServiceException;
	
	List<SysAnnoTypeInfo> findAll(String appId);
}
