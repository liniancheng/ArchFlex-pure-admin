package com.adtec.rdc.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;

/**
 * @author adtec
 * @date 2020-06-30 09:14:51
 */
public interface SysWorkflowInstanceNodeOperMapper extends BaseMapper<SysWorkflowInstanceNodeOper> {
	void deleteByInstanceId(@Param("instanceId")Serializable instanceId);
	List<SysWorkflowInstanceNodeOper> queryNodeOpersByInstanceId(@Param("instanceId")String instanceId);
	List<SysWorkflowInstanceNodeOper> queryNodeOpersByInodeIds(@Param("inodeIds")List<String> inodeIds);
}
