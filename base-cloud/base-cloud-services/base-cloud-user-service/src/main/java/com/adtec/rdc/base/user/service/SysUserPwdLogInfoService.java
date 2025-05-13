package com.adtec.rdc.base.user.service;

import java.time.LocalDateTime;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.user.model.po.SysUserPwdLogInfo;
import com.adtec.rdc.base.user.model.query.SysUserPwdLogInfoQuery;

/**
 * @author dengchf
 * @date 2021-02-07 14:34:42
 */
public interface SysUserPwdLogInfoService extends BaseService<SysUserPwdLogInfo> {
	SysUserPwdLogInfoQuery pageByQuery(SysUserPwdLogInfoQuery query);
	
	/**
	 * 判断密码是否修改过
	 */
	LocalDateTime lastModifyTime(String loginName);
}
