package com.littlelee.base.workflow.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.po.SysWorkflowTypeInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowTypeInfoQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:13:45
 */
public interface SysWorkflowTypeInfoService extends BaseService<SysWorkflowTypeInfo> {
	SysWorkflowTypeInfoQuery pageByQuery(SysWorkflowTypeInfoQuery query);
}
