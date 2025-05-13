package com.adtec.rdc.base.workflow.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;
import com.adtec.rdc.base.workflow.model.query.SysUserTaskExtQuery;

/**
 * @author adtec
 * @date 2020-07-20 19:19:57
 */
public interface SysUserTaskExtService extends BaseService<SysUserTaskExt> {
	SysUserTaskExtQuery pageByQuery(SysUserTaskExtQuery query);
}
