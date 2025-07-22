package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.feign.bo.WorkflowCreate;
import com.littlelee.base.workflow.feign.bo.WorkflowQuery;
import com.littlelee.base.workflow.model.bo.Workflow;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.WorkflowInfoQuery;

public interface WorkflowService extends BaseService<Workflow> {
	WorkflowInfoQuery pageByQuery(WorkflowInfoQuery query);
	String create(WorkflowCreate workflowCreate);
	WorkflowQuery query(String instanceId);
	List<WorkflowQuery> status(List<String> ids);
	SysWorkflowInstanceNodeOper update(Workflow workflow, String userId, String loginName);
}
