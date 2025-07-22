package com.littlelee.base.layout.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.enums.ErrorCodeEnum;
import com.littlelee.base.layout.mapper.SysRoleLayoutItemRelMapper;
import com.littlelee.base.layout.model.po.SysRoleLayoutItemRel;
import com.littlelee.base.layout.service.SysRoleLayoutItemRelService;
import com.littlelee.web.antd.bo.TransferNode;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2020-08-25 15:05:27
 */
@Service
public class SysRoleLayoutItemRelServiceImpl extends BaseServiceImpl<SysRoleLayoutItemRelMapper, SysRoleLayoutItemRel> implements SysRoleLayoutItemRelService {
	@Autowired
    private SysRoleLayoutItemRelMapper mapper;

	@Override
	public boolean saveItemRel(String itemId, List<String> roleIds) {
		if (StringUtils.isEmpty(itemId)) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_LAYID.getMessage());
		}
		if (roleIds == null || roleIds.size() == 0) {
			throw new ServiceException(ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getErrorCode(),
					ErrorCodeEnum.LAYOUT_ROLE_NULL_ROLEID.getMessage());
		}
		List<SysRoleLayoutItemRel> list = new ArrayList<SysRoleLayoutItemRel>();
		roleIds.forEach(roleId -> {
			SysRoleLayoutItemRel rel = new SysRoleLayoutItemRel();
			rel.setItemId(itemId).setRoleId(roleId);
			list.add(rel);
		});
//		mapper.batchInsert(list);
		super.saveBatch(list);
		return Boolean.TRUE;
	}

	@Override
	public boolean deleteItemRel(String itemId, String appId) {
		QueryWrapper<SysRoleLayoutItemRel> query = new QueryWrapper<SysRoleLayoutItemRel>();
		query.lambda().eq(SysRoleLayoutItemRel::getItemId, itemId);
		mapper.delete(query);
		return Boolean.TRUE;
	}

	@Override
	public List<TransferNode> listRole(String itemId, String appId) {
		return mapper.listRole(appId, itemId);
	}

	@Override
	public boolean deleteItemRel(String itemId, List<String> roleIds) {
		QueryWrapper<SysRoleLayoutItemRel> query = new QueryWrapper<SysRoleLayoutItemRel>();
		query.lambda().eq(SysRoleLayoutItemRel::getItemId, itemId);
		mapper.delete(query);
		return Boolean.TRUE;
	}
}
