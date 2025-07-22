package com.littlelee.base.workflow.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.workflow.mapper.SysWorkflowInstanceNodeMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNode;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceNodeService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:01
 */
@Service
public class SysWorkflowInstanceNodeServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeMapper, SysWorkflowInstanceNode> implements SysWorkflowInstanceNodeService {
	@Autowired
    private SysWorkflowInstanceNodeMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeQuery pageByQuery(SysWorkflowInstanceNodeQuery query) {
		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
        return query;
	}
}
