package com.littlelee.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNode;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:01
 */
public interface SysWorkflowInstanceNodeMapper extends BaseMapper<SysWorkflowInstanceNode> {
	void batchInsert(@Param("instanceNodes")List<SysWorkflowInstanceNode> instanceNodes);
	void deleteByInstanceId(@Param("instanceId")Serializable instanceId);
	List<SysWorkflowInstanceNode> queryNodesByInstanceId(@Param("instanceId")String instanceId);
}
