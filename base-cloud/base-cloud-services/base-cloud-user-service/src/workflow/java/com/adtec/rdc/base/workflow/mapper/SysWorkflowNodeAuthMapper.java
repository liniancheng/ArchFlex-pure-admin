package com.adtec.rdc.base.workflow.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;

/**
 * @author adtec
 * @date 2020-06-30 09:14:34
 */
public interface SysWorkflowNodeAuthMapper extends BaseMapper<SysWorkflowNodeAuth> {
	void deleteByWorkflowId(@Param("workflowId")Serializable workflowId);
	List<String> queryObjIdsByNodeIdAndAuthType(@Param("nodeId")String nodeId, @Param("authType")String authType);
	void batchInsert(@Param("auths")List<SysWorkflowNodeAuth> auths);
}
