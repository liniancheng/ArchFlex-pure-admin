package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.bo.SysWorkflowMacroTree;
import com.littlelee.base.workflow.model.po.SysWorkflowMacroInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowMacroInfoQuery;

/**
 * @author littlelee
 * @date 2020-07-05 07:55:01
 */
public interface SysWorkflowMacroInfoService extends BaseService<SysWorkflowMacroInfo> {
	SysWorkflowMacroInfoQuery pageByQuery(SysWorkflowMacroInfoQuery query);
	List<SysWorkflowMacroTree> tree(SysWorkflowMacroInfoQuery query);
}
