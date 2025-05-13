package com.adtec.rdc.base.workflow.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowTypeInfoQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:13:45
 */
public interface SysWorkflowTypeInfoService extends BaseService<SysWorkflowTypeInfo> {
	SysWorkflowTypeInfoQuery pageByQuery(SysWorkflowTypeInfoQuery query);
}
