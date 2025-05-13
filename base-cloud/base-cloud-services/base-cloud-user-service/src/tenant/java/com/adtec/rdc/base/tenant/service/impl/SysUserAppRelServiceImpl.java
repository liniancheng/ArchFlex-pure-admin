package com.adtec.rdc.base.tenant.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.tenant.mapper.SysUserAppRelMapper;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.tenant.model.po.SysUserAppRel;
import com.adtec.rdc.base.tenant.service.SysUserAppRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@Service
public class SysUserAppRelServiceImpl extends BaseServiceImpl<SysUserAppRelMapper, SysUserAppRel> implements SysUserAppRelService {
	@Autowired
    private SysUserAppRelMapper mapper;

	@Override
	public Boolean saveAppRel(String appId, List<String> userIds) {
		if(StringUtils.isBlank(appId) || userIds == null ) {
			throw new ServiceException("参数为空，请检查！");
		}
		userIds.forEach(userId ->{
			if(StringUtils.isNotBlank(userId)) {
				SysUserAppRel rel = new SysUserAppRel();
				rel.setAppId(appId);
				rel.setUserId(userId);
				mapper.insert(rel);
			}
		});
		return Boolean.TRUE;
	}

	@Override
	public List<TransferVo> getUserList(String appId) {
		return mapper.getUserAppRels(appId);
	}

	@Override
	public Boolean deleteUserAppRel(String appId, List<String> userIds) {
		QueryWrapper<SysUserAppRel> query = new QueryWrapper<SysUserAppRel>();
		query.lambda().in(SysUserAppRel::getUserId,userIds).eq(SysUserAppRel::getAppId, appId);
		mapper.delete(query);
		return Boolean.TRUE;
	}
	
}
