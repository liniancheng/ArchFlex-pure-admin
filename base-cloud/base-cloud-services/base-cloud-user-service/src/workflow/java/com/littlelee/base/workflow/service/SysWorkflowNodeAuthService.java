package com.littlelee.base.workflow.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.user.model.po.SysRoleInfo;
import com.littlelee.base.workflow.model.po.SysWorkflowMacroInfo;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeAuth;
import com.littlelee.base.workflow.model.query.SysWorkflowNodeAuthQuery;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:34
 */
public interface SysWorkflowNodeAuthService extends BaseService<SysWorkflowNodeAuth> {
	SysWorkflowNodeAuthQuery pageByQuery(SysWorkflowNodeAuthQuery query);
	List<SysRoleInfo> roles(String nodeId, String appId);
	List<SysWorkflowMacroInfo> macros(String workflowId, String nodeId, String authType);
}
