package com.adtec.rdc.base.workflow.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;

/**
 * @author adtec
 * @date 2020-07-02 10:06:37
 */
public interface SysWorkflowInstanceNodeAuthService extends BaseService<SysWorkflowInstanceNodeAuth> {
	SysWorkflowInstanceNodeAuthQuery pageByQuery(SysWorkflowInstanceNodeAuthQuery query);
}
