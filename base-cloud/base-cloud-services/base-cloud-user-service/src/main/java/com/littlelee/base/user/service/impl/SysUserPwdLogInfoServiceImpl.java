package com.littlelee.base.user.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.user.mapper.SysUserPwdLogInfoMapper;
import com.littlelee.base.user.model.po.SysUserPwdLogInfo;
import com.littlelee.base.user.model.query.SysUserPwdLogInfoQuery;
import com.littlelee.base.user.service.SysUserPwdLogInfoService;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2021-02-07 14:34:42
 */
@Service
public class SysUserPwdLogInfoServiceImpl extends BaseServiceImpl<SysUserPwdLogInfoMapper, SysUserPwdLogInfo> implements SysUserPwdLogInfoService {
	@Autowired
    private SysUserPwdLogInfoMapper mapper;
	
	@Override
	public SysUserPwdLogInfoQuery pageByQuery(SysUserPwdLogInfoQuery query) {
		query.addOrder(OrderItem.desc("create_time"));
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public LocalDateTime lastModifyTime(String loginName) {
		SysUserPwdLogInfoQuery query = new SysUserPwdLogInfoQuery();
		query.setLoginName(loginName);
		query.setSize(1);
		query.addOrder(OrderItem.desc("create_time"));
		mapper.pageByQuery(query);
		if (query.getTotal() > 0) {
			return query.getRecords().get(0).getCreateTime();
		}
		return null;
	}
}
