package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeAuthMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceNodeAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author adtec
 * @date 2020-07-02 10:06:37
 */
@Service
public class SysWorkflowInstanceNodeAuthServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeAuthMapper, SysWorkflowInstanceNodeAuth> implements SysWorkflowInstanceNodeAuthService {
	@Autowired
    private SysWorkflowInstanceNodeAuthMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeAuthQuery pageByQuery(SysWorkflowInstanceNodeAuthQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
}
