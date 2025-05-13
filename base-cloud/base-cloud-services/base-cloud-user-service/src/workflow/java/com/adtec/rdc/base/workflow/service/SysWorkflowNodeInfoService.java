package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowNodeTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowNodeInfoQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:14:21
 */
public interface SysWorkflowNodeInfoService extends BaseService<SysWorkflowNodeInfo> {
	SysWorkflowNodeInfoQuery pageByQuery(SysWorkflowNodeInfoQuery query);
	List<SysWorkflowNodeTree> tree(SysWorkflowInfoQuery query);
	List<SysWorkflowNodeTree> parentNodes(String workflowId, int nodeLevel);
}
