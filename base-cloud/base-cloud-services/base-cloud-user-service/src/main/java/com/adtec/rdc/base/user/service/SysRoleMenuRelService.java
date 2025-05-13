package com.adtec.rdc.base.user.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;

public interface SysRoleMenuRelService extends BaseService<SysRoleMenuRel>{

	public Boolean saveRoleMenuRel(String roleId, List<String> menuIds, String appId);

	public Boolean deleteRoleMenuRel(String roleId, List<String> menuIds);

	public List<SysRoleMenuRel> getAllMenuTreeNode(String appId);
	
}
