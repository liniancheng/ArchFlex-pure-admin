package com.adtec.rdc.base.layout.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.enums.ErrorCodeEnum;
import com.adtec.rdc.base.layout.mapper.SysRoleLayoutRelMapper;
import com.adtec.rdc.base.layout.model.po.SysRoleLayoutRel;
import com.adtec.rdc.base.layout.service.SysRoleLayoutRelService;
import com.adtec.rdc.web.antd.bo.TransferNode;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dengchf
 * @date 2020-08-25 15:06:33
 */
@Service
public class SysRoleLayoutRelServiceImpl extends BaseServiceImpl<SysRoleLayoutRelMapper, SysRoleLayoutRel>
		implements SysRoleLayoutRelService {
	@Autowired
	private SysRoleLayoutRelMapper mapper;

	@Override
	public boolean saveLayoutRel(String layId, List<String> roleIds) {
		if (StringUtils.isEmpty(layId)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getMessage());
		}
		if (roleIds == null || roleIds.size() == 0) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getMessage());
		}
		List<SysRoleLayoutRel> list = new ArrayList<SysRoleLayoutRel>();
		roleIds.forEach(roleId -> {
			SysRoleLayoutRel rel = new SysRoleLayoutRel();
			rel.setLayId(layId).setRoleId(roleId);
			list.add(rel);
		});
		return super.saveBatch(list);
	}

	@Override
	public boolean deleteLayoutRel(String layId, List<String> roleIds) {
		if (StringUtils.isEmpty(layId)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getMessage());
		}
		if (roleIds == null || roleIds.size() == 0) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getMessage());
		}
		QueryWrapper<SysRoleLayoutRel> query = new QueryWrapper<SysRoleLayoutRel>();
		query.lambda().eq(SysRoleLayoutRel::getLayId, layId).in(SysRoleLayoutRel::getRoleId, roleIds);
		mapper.delete(query);
		return Boolean.TRUE;
	}

	@Override
	public boolean deleteLayoutRel(String layId, String appId) {
		QueryWrapper<SysRoleLayoutRel> query = new QueryWrapper<SysRoleLayoutRel>();
		query.lambda().eq(SysRoleLayoutRel::getLayId, layId);
		mapper.delete(query);
		return Boolean.TRUE;
	}

	@Override
	public List<TransferNode> listRole(String layId, String appId) {
		return mapper.listRole(appId, layId);
	}

}
