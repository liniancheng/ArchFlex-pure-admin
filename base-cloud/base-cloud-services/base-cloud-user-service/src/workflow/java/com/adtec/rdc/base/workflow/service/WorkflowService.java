package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowCreate;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.WorkflowInfoQuery;

public interface WorkflowService extends BaseService<Workflow> {
	WorkflowInfoQuery pageByQuery(WorkflowInfoQuery query);
	String create(WorkflowCreate workflowCreate);
	WorkflowQuery query(String instanceId);
	List<WorkflowQuery> status(List<String> ids);
	SysWorkflowInstanceNodeOper update(Workflow workflow, String userId, String loginName);
}
