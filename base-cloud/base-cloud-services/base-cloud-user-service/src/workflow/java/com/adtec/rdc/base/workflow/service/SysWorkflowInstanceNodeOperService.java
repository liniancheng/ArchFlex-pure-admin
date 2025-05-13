package com.adtec.rdc.base.workflow.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeOperQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:14:51
 */
public interface SysWorkflowInstanceNodeOperService extends BaseService<SysWorkflowInstanceNodeOper> {
	SysWorkflowInstanceNodeOperQuery pageByQuery(SysWorkflowInstanceNodeOperQuery query);
}
