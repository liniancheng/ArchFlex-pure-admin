package com.littlelee.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeAuth;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:34
 */
public interface SysWorkflowNodeAuthMapper extends BaseMapper<SysWorkflowNodeAuth> {
	void deleteByWorkflowId(@Param("workflowId")Serializable workflowId);
	List<String> queryObjIdsByNodeIdAndAuthType(@Param("nodeId")String nodeId, @Param("authType")String authType);
	void batchInsert(@Param("auths")List<SysWorkflowNodeAuth> auths);
}
