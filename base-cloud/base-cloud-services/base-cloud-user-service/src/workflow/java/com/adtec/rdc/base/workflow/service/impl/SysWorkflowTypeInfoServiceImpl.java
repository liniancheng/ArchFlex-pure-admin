package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowTypeInfoMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowTypeInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowTypeInfoService;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-06-30 09:13:45
 */
@Service
public class SysWorkflowTypeInfoServiceImpl extends BaseServiceImpl<SysWorkflowTypeInfoMapper, SysWorkflowTypeInfo> implements SysWorkflowTypeInfoService {
	@Autowired
    private SysWorkflowTypeInfoMapper mapper;
	
	@Override
	public SysWorkflowTypeInfoQuery pageByQuery(SysWorkflowTypeInfoQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	@Transactional
	public boolean save(SysWorkflowTypeInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_TYPE_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_TYPE_RE_NAME.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysWorkflowTypeInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_TYPE_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_TYPE_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		//检查是否存在元数据
		if(mapper.isExistWorkflow(id.toString())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_TYPE_HAS_WORKFLOW.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_TYPE_HAS_WORKFLOW.getMessage());
		}
		return super.removeById(id);
	}
	
	private boolean isExistName(SysWorkflowTypeInfo entity) {
		return mapper.isExistName(entity);
	}
}
