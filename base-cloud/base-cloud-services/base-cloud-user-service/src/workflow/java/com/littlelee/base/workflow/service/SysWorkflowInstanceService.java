package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.model.bo.mxgraph.MxGraphBean;
import com.littlelee.base.workflow.model.bo.SysWorkflowTree;
import com.littlelee.base.workflow.model.po.SysWorkflowInstance;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:15
 */
public interface SysWorkflowInstanceService extends BaseService<SysWorkflowInstance> {
	SysWorkflowInstanceQuery pageByQuery(SysWorkflowInstanceQuery query);
	List<SysWorkflowTree> tree(SysWorkflowInstanceQuery query);
	MxGraphBean graph(String instanceId);
}
