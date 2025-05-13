package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphBean;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:15:15
 */
public interface SysWorkflowInstanceService extends BaseService<SysWorkflowInstance> {
	SysWorkflowInstanceQuery pageByQuery(SysWorkflowInstanceQuery query);
	List<SysWorkflowTree> tree(SysWorkflowInstanceQuery query);
	MxGraphBean graph(String instanceId);
}
