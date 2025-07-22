package com.littlelee.base.workflow.mapper;

import com.littlelee.base.workflow.model.po.SysWorkflowMacroInfo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2020-07-05 07:55:01
 */
public interface SysWorkflowMacroInfoMapper extends BaseMapper<SysWorkflowMacroInfo> {
	boolean isExistName(SysWorkflowMacroInfo entity);
	boolean isExistCode(SysWorkflowMacroInfo entity);
	void deleteByWorkflowId(@Param("workflowId")Serializable workflowId);
	void batchInsert(@Param("macros")List<SysWorkflowMacroInfo> macros);
}
