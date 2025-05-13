package com.adtec.rdc.base.user.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.user.mapper.SysUserGroupRelMapper;
import com.adtec.rdc.base.user.model.po.SysUserGroupRel;
import com.adtec.rdc.base.user.service.SysUserGroupRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@Service
public class SysUserGroupRelServiceImpl extends BaseServiceImpl<SysUserGroupRelMapper, SysUserGroupRel> implements SysUserGroupRelService {
	@Autowired
    private SysUserGroupRelMapper mapper;

	@Override
	public Boolean saveGroupRel(String groupId, List<String> userIds, String appId) {
		if(StringUtils.isBlank(groupId) || userIds == null ) {
			throw new ServiceException("参数为空，请检查！");
		}
		userIds.forEach(userId ->{
			if(StringUtils.isNotBlank(userId)) {
				SysUserGroupRel rel = new SysUserGroupRel();
				rel.setGroupId(groupId);
				rel.setUserId(userId);
				mapper.insert(rel);
			}
		});
		return Boolean.TRUE;
	}

	@Override
	public List<TransferVo> getUserList(String groupId) {
		return mapper.getUserGroupRels(groupId);
	}

	@Override
	public Boolean deleteUserGroupRel(String groupId, List<String> userIds) {
		QueryWrapper<SysUserGroupRel> query = new QueryWrapper<SysUserGroupRel>();
		query.lambda().in(SysUserGroupRel::getUserId,userIds).eq(SysUserGroupRel::getGroupId, groupId);
		mapper.delete(query);
		return Boolean.TRUE;
	}
	
}
