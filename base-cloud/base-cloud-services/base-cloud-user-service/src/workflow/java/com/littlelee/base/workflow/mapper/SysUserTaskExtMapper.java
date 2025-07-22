package com.littlelee.base.workflow.mapper;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.workflow.model.po.SysUserTaskExt;

/**
 * @author littlelee
 * @date 2020-07-20 19:19:57
 */
public interface SysUserTaskExtMapper extends BaseMapper<SysUserTaskExt> {
	boolean isExistName(SysUserTaskExt entity);
}
