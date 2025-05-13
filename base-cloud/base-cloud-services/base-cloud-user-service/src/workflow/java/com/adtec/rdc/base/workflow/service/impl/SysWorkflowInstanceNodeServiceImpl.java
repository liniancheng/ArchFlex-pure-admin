package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceNodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author adtec
 * @date 2020-06-30 09:15:01
 */
@Service
public class SysWorkflowInstanceNodeServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeMapper, SysWorkflowInstanceNode> implements SysWorkflowInstanceNodeService {
	@Autowired
    private SysWorkflowInstanceNodeMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeQuery pageByQuery(SysWorkflowInstanceNodeQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
}
