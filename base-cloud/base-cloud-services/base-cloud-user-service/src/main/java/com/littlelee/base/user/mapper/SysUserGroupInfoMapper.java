package com.littlelee.base.user.mapper;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.user.model.po.SysUserGroupInfo;
import com.littlelee.base.user.model.query.SysUserGroupInfoQuery;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
public interface SysUserGroupInfoMapper extends BaseMapper<SysUserGroupInfo> {

	SysUserGroupInfoQuery loadGroupById(String id);
	
	Integer getGroupByName(String groupName);
	
	Integer getGroupByNameAndId(String groupName, String groupId);

}
