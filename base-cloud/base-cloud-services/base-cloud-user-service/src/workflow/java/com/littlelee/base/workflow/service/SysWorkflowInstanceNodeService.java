package com.littlelee.base.workflow.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNode;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:01
 */
public interface SysWorkflowInstanceNodeService extends BaseService<SysWorkflowInstanceNode> {
	SysWorkflowInstanceNodeQuery pageByQuery(SysWorkflowInstanceNodeQuery query);
}
