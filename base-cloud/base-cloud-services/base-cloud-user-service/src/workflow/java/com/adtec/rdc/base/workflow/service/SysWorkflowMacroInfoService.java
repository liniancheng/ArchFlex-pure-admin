package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowMacroTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowMacroInfoQuery;

/**
 * @author adtec
 * @date 2020-07-05 07:55:01
 */
public interface SysWorkflowMacroInfoService extends BaseService<SysWorkflowMacroInfo> {
	SysWorkflowMacroInfoQuery pageByQuery(SysWorkflowMacroInfoQuery query);
	List<SysWorkflowMacroTree> tree(SysWorkflowMacroInfoQuery query);
}
