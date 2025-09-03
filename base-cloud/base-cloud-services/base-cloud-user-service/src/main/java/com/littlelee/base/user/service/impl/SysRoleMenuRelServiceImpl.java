package com.littlelee.base.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.user.mapper.SysRoleMenuRelMapper;
import com.littlelee.base.user.model.po.SysRoleMenuRel;
import com.littlelee.base.user.service.SysRoleMenuRelService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author liushp
 */
@Service
public class SysRoleMenuRelServiceImpl extends BaseServiceImpl<SysRoleMenuRelMapper, SysRoleMenuRel> implements SysRoleMenuRelService {
	@Autowired
    private SysRoleMenuRelMapper mapper;

	@Override
	public Boolean saveRoleMenuRel(String roleId, List<String> menuIds, String appId) {
		if(StringUtils.isBlank(roleId) || menuIds == null ) {
			throw new ServiceException("参数为空，请检查！");
		}
		List<SysRoleMenuRel> rels = new ArrayList<SysRoleMenuRel>();
		menuIds.forEach(menuId ->{
			if(StringUtils.isNotBlank(menuId)) {
				SysRoleMenuRel rel = new SysRoleMenuRel();
				rel.setRoleId(roleId);
				rel.setMenuId(menuId);
				rels.add(rel);
			}
		});
		mapper.batchInsertMenuRel(rels);
		return Boolean.TRUE;
	}

	@Override
	public Boolean deleteRoleMenuRel(String roleId, List<String> menuIds) {
		QueryWrapper<SysRoleMenuRel> query = new QueryWrapper<SysRoleMenuRel>();
		query.lambda().eq(SysRoleMenuRel::getRoleId, roleId).in(SysRoleMenuRel::getMenuId, menuIds);
		mapper.delete(query);
		return Boolean.TRUE;
	}

	@Override
	public List<SysRoleMenuRel> getAllMenuTreeNode(String roleId) {
        return mapper.getRoleMenuRels(roleId);
	}

    @Override
    public List<String> getAllMenuIds(String roleId) {
        return mapper.getRoleMenuIds(roleId);
	}
	
}
