package com.littlelee.base.workflow.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeOperQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:51
 */
public interface SysWorkflowInstanceNodeOperService extends BaseService<SysWorkflowInstanceNodeOper> {
	SysWorkflowInstanceNodeOperQuery pageByQuery(SysWorkflowInstanceNodeOperQuery query);
}
