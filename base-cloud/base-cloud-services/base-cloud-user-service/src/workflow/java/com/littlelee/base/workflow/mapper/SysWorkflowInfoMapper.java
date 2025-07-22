package com.littlelee.base.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInfo;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:26
 */
public interface SysWorkflowInfoMapper extends BaseMapper<SysWorkflowInfo> {
	boolean isExistName(SysWorkflowInfo entity);
	boolean isExistCode(SysWorkflowInfo entity);
	boolean isExistInstance(@Param("workflowId")String workflowId);
	List<SysWorkflowInfo> queryWorkflowListForSearch(@Param("workflowName")String workflowName, @Param("workflowRmk")String workflowRmk, @Param("appId")String appId);
	SysWorkflowInfo selectPreviousVersionInfoByWorkflowCodeAndVersionNum(@Param("workflowCode")String workflowCode, @Param("versionNum")String versionNum);
	List<SysWorkflowInfo> queryWorkflowListForCreate(@Param("workflowCode")String workflowCode, @Param("appId")String appId);
}
