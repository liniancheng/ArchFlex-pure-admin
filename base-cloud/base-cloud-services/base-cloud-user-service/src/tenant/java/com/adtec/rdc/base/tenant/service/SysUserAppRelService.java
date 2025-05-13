package com.adtec.rdc.base.tenant.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;
import com.adtec.rdc.base.tenant.model.po.SysUserAppRel;

/**
 * @author liushp
 */
public interface SysUserAppRelService extends BaseService<SysUserAppRel> {

	Boolean saveAppRel(String appId, List<String> userIds);

	List<TransferVo> getUserList(String appId);

	Boolean deleteUserAppRel(String appId, List<String> userIds);
	
}