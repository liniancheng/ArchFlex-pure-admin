package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.bo.SysWorkflowCopy;
import com.littlelee.base.workflow.model.bo.SysWorkflowTree;
import com.littlelee.base.workflow.model.po.SysWorkflowInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowInfoQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:26
 */
public interface SysWorkflowInfoService extends BaseService<SysWorkflowInfo> {
	SysWorkflowInfoQuery pageByQuery(SysWorkflowInfoQuery query);
	List<SysWorkflowTree> tree(SysWorkflowInfoQuery query);
	Boolean copy(SysWorkflowCopy sysWorkflowCopy);
}
