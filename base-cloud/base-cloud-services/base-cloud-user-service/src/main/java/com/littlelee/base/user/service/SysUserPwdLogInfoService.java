package com.littlelee.base.user.service;

import java.time.LocalDateTime;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.user.model.po.SysUserPwdLogInfo;
import com.littlelee.base.user.model.query.SysUserPwdLogInfoQuery;

/**
 * @author littlelee
 * @date 2021-02-07 14:34:42
 */
public interface SysUserPwdLogInfoService extends BaseService<SysUserPwdLogInfo> {
	SysUserPwdLogInfoQuery pageByQuery(SysUserPwdLogInfoQuery query);
	
	/**
	 * 判断密码是否修改过
	 */
	LocalDateTime lastModifyTime(String loginName);
}
