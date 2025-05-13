package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowMacroInfoMapper;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowMacroTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowMacroInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowMacroInfoService;
import com.adtec.rdc.base.workflow.util.SysWorkflowMacroTreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-07-05 07:55:01
 */
@Service
public class SysWorkflowMacroInfoServiceImpl extends BaseServiceImpl<SysWorkflowMacroInfoMapper, SysWorkflowMacroInfo> implements SysWorkflowMacroInfoService {
	@Autowired
    private SysWorkflowMacroInfoMapper mapper;
	
	@Override
	public SysWorkflowMacroInfoQuery pageByQuery(SysWorkflowMacroInfoQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	@Transactional
	public boolean save(SysWorkflowMacroInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_MACRO_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_MACRO_RE_NAME.getMessage());
		}
		if (isExistCode(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_MACRO_RE_CODE.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_MACRO_RE_CODE.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysWorkflowMacroInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_MACRO_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_MACRO_RE_NAME.getMessage());
		}
		if (isExistCode(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_MACRO_RE_CODE.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_MACRO_RE_CODE.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	private boolean isExistName(SysWorkflowMacroInfo entity) {
		return mapper.isExistName(entity);
	}
	
	private boolean isExistCode(SysWorkflowMacroInfo entity) {
		return mapper.isExistCode(entity);
	}

	@Override
	public List<SysWorkflowMacroTree> tree(SysWorkflowMacroInfoQuery query) {
		QueryWrapper<SysWorkflowMacroInfo> macroQuery = new QueryWrapper<SysWorkflowMacroInfo>();
		macroQuery.lambda().eq(SysWorkflowMacroInfo::getWorkflowId, query.getWorkflowId()).orderByAsc(SysWorkflowMacroInfo::getMacroCode);
    	List<SysWorkflowMacroInfo> macros = mapper.selectList(macroQuery);
    	return SysWorkflowMacroTreeUtils.tree(macros);
	}

}
