package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.bo.SysWorkflowNodeTree;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowInfoQuery;
import com.littlelee.base.workflow.model.query.SysWorkflowNodeInfoQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:21
 */
public interface SysWorkflowNodeInfoService extends BaseService<SysWorkflowNodeInfo> {
	SysWorkflowNodeInfoQuery pageByQuery(SysWorkflowNodeInfoQuery query);
	List<SysWorkflowNodeTree> tree(SysWorkflowInfoQuery query);
	List<SysWorkflowNodeTree> parentNodes(String workflowId, int nodeLevel);
}
