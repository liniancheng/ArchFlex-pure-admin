package com.littlelee.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:51
 */
public interface SysWorkflowInstanceNodeOperMapper extends BaseMapper<SysWorkflowInstanceNodeOper> {
	void deleteByInstanceId(@Param("instanceId")Serializable instanceId);
	List<SysWorkflowInstanceNodeOper> queryNodeOpersByInstanceId(@Param("instanceId")String instanceId);
	List<SysWorkflowInstanceNodeOper> queryNodeOpersByInodeIds(@Param("inodeIds")List<String> inodeIds);
}
