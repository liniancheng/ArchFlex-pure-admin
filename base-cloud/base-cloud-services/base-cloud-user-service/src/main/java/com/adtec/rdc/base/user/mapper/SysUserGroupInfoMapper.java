package com.adtec.rdc.base.user.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.user.model.po.SysUserGroupInfo;
import com.adtec.rdc.base.user.model.query.SysUserGroupInfoQuery;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
public interface SysUserGroupInfoMapper extends BaseMapper<SysUserGroupInfo> {

	SysUserGroupInfoQuery loadGroupById(String id);
	
	Integer getGroupByName(String groupName);
	
	Integer getGroupByNameAndId(String groupName, String groupId);

}
