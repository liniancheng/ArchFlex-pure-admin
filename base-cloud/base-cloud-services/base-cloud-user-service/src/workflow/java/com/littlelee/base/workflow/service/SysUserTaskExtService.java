package com.littlelee.base.workflow.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.workflow.model.po.SysUserTaskExt;
import com.littlelee.base.workflow.model.query.SysUserTaskExtQuery;

/**
 * @author littlelee
 * @date 2020-07-20 19:19:57
 */
public interface SysUserTaskExtService extends BaseService<SysUserTaskExt> {
	SysUserTaskExtQuery pageByQuery(SysUserTaskExtQuery query);
}
