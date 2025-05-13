package com.adtec.rdc.base.workflow.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:15:01
 */
public interface SysWorkflowInstanceNodeService extends BaseService<SysWorkflowInstanceNode> {
	SysWorkflowInstanceNodeQuery pageByQuery(SysWorkflowInstanceNodeQuery query);
}
