package com.adtec.rdc.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;

/**
 * @author adtec
 * @date 2020-06-30 09:15:01
 */
public interface SysWorkflowInstanceNodeMapper extends BaseMapper<SysWorkflowInstanceNode> {
	void batchInsert(@Param("instanceNodes")List<SysWorkflowInstanceNode> instanceNodes);
	void deleteByInstanceId(@Param("instanceId")Serializable instanceId);
	List<SysWorkflowInstanceNode> queryNodesByInstanceId(@Param("instanceId")String instanceId);
}
