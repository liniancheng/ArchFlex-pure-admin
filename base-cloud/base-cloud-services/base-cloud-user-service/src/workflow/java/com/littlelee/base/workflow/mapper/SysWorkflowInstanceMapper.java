package com.littlelee.base.workflow.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstance;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:15
 */
public interface SysWorkflowInstanceMapper extends BaseMapper<SysWorkflowInstance> {
	List<SysWorkflowInstance> queryInstanceListForSearch(@Param("instanceName")String instanceName, @Param("instanceStatus")String instanceStatus, @Param("appId")String appId);
}
