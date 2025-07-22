package com.littlelee.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeInfo;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:21
 */
public interface SysWorkflowNodeInfoMapper extends BaseMapper<SysWorkflowNodeInfo> {
	boolean isExistName(SysWorkflowNodeInfo entity);
	boolean isExistChild(@Param("nodeId")String nodeId);
	void deleteByWorkflowId(@Param("workflowId")Serializable workflowId);
	void batchInsert(@Param("nodes")List<SysWorkflowNodeInfo> nodes);
}
