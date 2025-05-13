package com.adtec.rdc.base.workflow.mapper;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author adtec
 * @date 2020-07-05 07:55:01
 */
public interface SysWorkflowMacroInfoMapper extends BaseMapper<SysWorkflowMacroInfo> {
	boolean isExistName(SysWorkflowMacroInfo entity);
	boolean isExistCode(SysWorkflowMacroInfo entity);
	void deleteByWorkflowId(@Param("workflowId")Serializable workflowId);
	void batchInsert(@Param("macros")List<SysWorkflowMacroInfo> macros);
}
