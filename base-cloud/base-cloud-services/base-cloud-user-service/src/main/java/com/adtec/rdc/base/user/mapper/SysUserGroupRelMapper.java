package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.user.model.po.SysUserGroupRel;


/**
 * <p>
 * 用户组-用户关联表
 * </p>
 * 
 * @author Liushp
 * @date 2019-12-05
 */
public interface SysUserGroupRelMapper extends BaseMapper<SysUserGroupRel>{

	void removeByIds(@Param("userIds")List<String> userIds, @Param("groupId")String groupId);

	List<TransferVo> getUserGroupRels(String groupId);

	
}
