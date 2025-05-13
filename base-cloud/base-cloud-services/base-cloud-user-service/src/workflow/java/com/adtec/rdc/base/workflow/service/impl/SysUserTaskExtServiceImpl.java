package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.mapper.SysUserTaskExtMapper;
import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;
import com.adtec.rdc.base.workflow.model.query.SysUserTaskExtQuery;
import com.adtec.rdc.base.workflow.service.SysUserTaskExtService;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-07-20 19:19:57
 */
@Service
public class SysUserTaskExtServiceImpl extends BaseServiceImpl<SysUserTaskExtMapper, SysUserTaskExt> implements SysUserTaskExtService {
	@Autowired
    private SysUserTaskExtMapper mapper;
	
	@Override
	public SysUserTaskExtQuery pageByQuery(SysUserTaskExtQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	@Transactional
	public boolean save(SysUserTaskExt entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.USER_TASK_EXT_RE_NAME.getErrorCode(),
					ErrorCodeEnums.USER_TASK_EXT_RE_NAME.getMessage());
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysUserTaskExt entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.USER_TASK_EXT_RE_NAME.getErrorCode(),
					ErrorCodeEnums.USER_TASK_EXT_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		return super.removeById(id);
	}
	
	private boolean isExistName(SysUserTaskExt entity) {
		return mapper.isExistName(entity);
	}
	
}
