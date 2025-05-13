package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.user.model.po.SysGroupBranchRel;

/**
 * <p>
 * 用户组-机构关联表
 * </p>
 * 
 * @author Liushp
 * @date 2019-12-05
 */
public interface SysGroupBranchRelMapper extends BaseMapper<SysGroupBranchRel>{
	
	
	void removeByIds(@Param("branchNos")List<String> branchNos, @Param("groupId")String groupId);
	
}
