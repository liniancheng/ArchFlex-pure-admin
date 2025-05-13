package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeOperMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeOperQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceNodeOperService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author adtec
 * @date 2020-06-30 09:14:51
 */
@Service
public class SysWorkflowInstanceNodeOperServiceImpl extends BaseServiceImpl<SysWorkflowInstanceNodeOperMapper, SysWorkflowInstanceNodeOper> implements SysWorkflowInstanceNodeOperService {
	@Autowired
    private SysWorkflowInstanceNodeOperMapper mapper;
	
	@Override
	public SysWorkflowInstanceNodeOperQuery pageByQuery(SysWorkflowInstanceNodeOperQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
}
