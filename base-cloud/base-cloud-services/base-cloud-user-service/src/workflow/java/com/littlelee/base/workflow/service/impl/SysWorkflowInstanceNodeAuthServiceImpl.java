package com.littlelee.base.workflow.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.workflow.mapper.SysWorkflowInstanceNodeAuthMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceNodeAuthService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2020-07-02 10:06:37
 */
@Service
public class SysWorkflowInstanceNodeAuthServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeAuthMapper, SysWorkflowInstanceNodeAuth> implements SysWorkflowInstanceNodeAuthService {
	@Autowired
    private SysWorkflowInstanceNodeAuthMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeAuthQuery pageByQuery(SysWorkflowInstanceNodeAuthQuery query) {
		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
        return query;
	}
}
