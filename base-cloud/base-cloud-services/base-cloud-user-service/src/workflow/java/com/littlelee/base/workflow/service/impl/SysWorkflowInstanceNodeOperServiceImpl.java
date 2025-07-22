package com.littlelee.base.workflow.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.workflow.mapper.SysWorkflowInstanceNodeOperMapper;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeOperQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceNodeOperService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2020-06-30 09:14:51
 */
@Service
public class SysWorkflowInstanceNodeOperServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeOperMapper, SysWorkflowInstanceNodeOper> implements SysWorkflowInstanceNodeOperService {
	@Autowired
    private SysWorkflowInstanceNodeOperMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeOperQuery pageByQuery(SysWorkflowInstanceNodeOperQuery query) {
		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
		mapper.pageByQuery(query);
        return query;
	}
}
