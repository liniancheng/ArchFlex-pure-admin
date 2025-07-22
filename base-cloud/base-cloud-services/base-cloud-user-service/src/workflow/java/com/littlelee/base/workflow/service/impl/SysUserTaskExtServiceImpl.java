package com.littlelee.base.workflow.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.workflow.enums.ErrorCodeEnums;
import com.littlelee.base.workflow.mapper.SysUserTaskExtMapper;
import com.littlelee.base.workflow.model.po.SysUserTaskExt;
import com.littlelee.base.workflow.model.query.SysUserTaskExtQuery;
import com.littlelee.base.workflow.service.SysUserTaskExtService;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author littlelee
 * @date 2020-07-20 19:19:57
 */
@Service
public class SysUserTaskExtServiceImpl extends BaseServiceImpl<SysUserTaskExtMapper, SysUserTaskExt> implements SysUserTaskExtService {
	@Autowired
    private SysUserTaskExtMapper mapper;
	
	@Override
	public SysUserTaskExtQuery pageByQuery(SysUserTaskExtQuery query) {
		query.addOrder(OrderItem.desc("create_time")).addOrder(OrderItem.desc("modify_time"));
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
