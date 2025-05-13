package com.adtec.rdc.base.workflow.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowNodeAuthQuery;

/**
 * @author adtec
 * @date 2020-06-30 09:14:34
 */
public interface SysWorkflowNodeAuthService extends BaseService<SysWorkflowNodeAuth> {
	SysWorkflowNodeAuthQuery pageByQuery(SysWorkflowNodeAuthQuery query);
	List<SysRoleInfo> roles(String nodeId, String appId);
	List<SysWorkflowMacroInfo> macros(String workflowId, String nodeId, String authType);
}
