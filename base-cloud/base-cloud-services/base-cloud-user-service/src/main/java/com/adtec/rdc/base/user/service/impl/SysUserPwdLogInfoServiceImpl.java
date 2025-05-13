package com.adtec.rdc.base.user.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.user.mapper.SysUserPwdLogInfoMapper;
import com.adtec.rdc.base.user.model.po.SysUserPwdLogInfo;
import com.adtec.rdc.base.user.model.query.SysUserPwdLogInfoQuery;
import com.adtec.rdc.base.user.service.SysUserPwdLogInfoService;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengchf
 * @date 2021-02-07 14:34:42
 */
@Service
public class SysUserPwdLogInfoServiceImpl extends BaseServiceImpl<SysUserPwdLogInfoMapper, SysUserPwdLogInfo> implements SysUserPwdLogInfoService {
	@Autowired
    private SysUserPwdLogInfoMapper mapper;
	
	@Override
	public SysUserPwdLogInfoQuery pageByQuery(SysUserPwdLogInfoQuery query) {
		query.setDesc("create_time");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public LocalDateTime lastModifyTime(String loginName) {
		SysUserPwdLogInfoQuery query = new SysUserPwdLogInfoQuery();
		query.setLoginName(loginName);
		query.setSize(1);
		query.setDesc("create_time");
		mapper.pageByQuery(query);
		if (query.getTotal() > 0) {
			return query.getRecords().get(0).getCreateTime();
		}
		return null;
	}
}
