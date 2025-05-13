package com.adtec.rdc.base.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;

/**
 * @author adtec
 * @date 2020-06-30 09:15:15
 */
public interface SysWorkflowInstanceMapper extends BaseMapper<SysWorkflowInstance> {
	List<SysWorkflowInstance> queryInstanceListForSearch(@Param("instanceName")String instanceName, @Param("instanceStatus")String instanceStatus, @Param("appId")String appId);
}
