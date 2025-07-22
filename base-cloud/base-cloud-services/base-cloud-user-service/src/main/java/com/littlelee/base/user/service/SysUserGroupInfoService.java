package com.littlelee.base.user.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.user.model.po.SysUserGroupInfo;
import com.littlelee.base.user.model.query.SysUserGroupInfoQuery;

/**
 * @author liushp
 * @date 2019-12-05 
 */
public interface SysUserGroupInfoService extends BaseService<SysUserGroupInfo> {
	/**
	 * 分页查询
	 * @param query
	 * @return SysUserGroupInfoQuery
	 */
	SysUserGroupInfoQuery pageByQuery(SysUserGroupInfoQuery query);
	
	/** 
	 * 新增用户组 含有关联信息
	 * @param group
	 * @return boolean
	 *  
	 */
	boolean saveGroup(SysUserGroupInfo group);
	
	/**
	 * 根据id更新用户组
	 * @param groupId
	 * @return boolean
	 */
	boolean updateGroupById(SysUserGroupInfo group);
	
	/**
	 * 根据id删除用户组
	 * @param groupId
	 * @return boolean
	 */
	boolean deleteById(String groupId);
	
	/**
	 * 根据组id查询用户组信息与其绑定的用户id和机构号
	 * @param groupId
	 * @return
	 */
	SysUserGroupInfoQuery findGroupListById(String groupId);
}
