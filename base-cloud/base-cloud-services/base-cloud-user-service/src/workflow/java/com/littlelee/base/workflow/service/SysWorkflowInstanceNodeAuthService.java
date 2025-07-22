package com.littlelee.base.workflow.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;

/**
 * @author littlelee
 * @date 2020-07-02 10:06:37
 */
public interface SysWorkflowInstanceNodeAuthService extends BaseService<SysWorkflowInstanceNodeAuth> {
	SysWorkflowInstanceNodeAuthQuery pageByQuery(SysWorkflowInstanceNodeAuthQuery query);
}
