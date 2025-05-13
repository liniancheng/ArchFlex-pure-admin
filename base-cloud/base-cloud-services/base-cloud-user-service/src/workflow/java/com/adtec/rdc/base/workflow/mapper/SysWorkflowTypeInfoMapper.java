package com.adtec.rdc.base.workflow.mapper;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;

/**
 * @author adtec
 * @date 2020-06-30 09:13:45
 */
public interface SysWorkflowTypeInfoMapper extends BaseMapper<SysWorkflowTypeInfo> {
	boolean isExistName(SysWorkflowTypeInfo entity);
	boolean isExistWorkflow(@Param("typeId")String typeId);
}
