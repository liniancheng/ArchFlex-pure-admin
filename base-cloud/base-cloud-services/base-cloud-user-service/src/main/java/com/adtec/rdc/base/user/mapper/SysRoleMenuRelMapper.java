package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;

/**
 * <p>
 * 角色资源关联表 Mapper 接口
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
public interface SysRoleMenuRelMapper extends BaseMapper<SysRoleMenuRel> {
	/**
	 * 批量保存菜单权限
	 * @param menuRels
	 * @return
	 */
	int batchInsertMenuRel(@Param("menuRels") List<SysRoleMenuRel> menuRels);
	
	void removeByIds(@Param("menuIds")List<String> menuIds, @Param("roleId")String roleId);

	List<SysRoleMenuRel> getRoleMenuRels(@Param("roleId")String roleId);
}
