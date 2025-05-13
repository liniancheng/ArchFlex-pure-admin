package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowCopy;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:15:26
 */
public interface SysWorkflowInfoService extends BaseService<SysWorkflowInfo> {
	SysWorkflowInfoQuery pageByQuery(SysWorkflowInfoQuery query);
	List<SysWorkflowTree> tree(SysWorkflowInfoQuery query);
	Boolean copy(SysWorkflowCopy sysWorkflowCopy);
}
