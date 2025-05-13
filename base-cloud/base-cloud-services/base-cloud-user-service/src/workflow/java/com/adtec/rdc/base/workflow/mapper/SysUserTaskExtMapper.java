package com.adtec.rdc.base.workflow.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;

/**
 * @author adtec
 * @date 2020-07-20 19:19:57
 */
public interface SysUserTaskExtMapper extends BaseMapper<SysUserTaskExt> {
	boolean isExistName(SysUserTaskExt entity);
}
