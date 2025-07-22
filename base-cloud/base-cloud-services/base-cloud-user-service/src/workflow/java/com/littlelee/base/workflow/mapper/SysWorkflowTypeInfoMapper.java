package com.littlelee.base.workflow.mapper;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowTypeInfo;

/**
 * @author littlelee
 * @date 2020-06-30 09:13:45
 */
public interface SysWorkflowTypeInfoMapper extends BaseMapper<SysWorkflowTypeInfo> {
	boolean isExistName(SysWorkflowTypeInfo entity);
	boolean isExistWorkflow(@Param("typeId")String typeId);
}
