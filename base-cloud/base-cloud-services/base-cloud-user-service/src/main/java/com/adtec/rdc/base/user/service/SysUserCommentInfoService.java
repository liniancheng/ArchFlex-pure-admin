package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.user.model.po.SysUserCommentInfo;
import com.adtec.rdc.base.user.model.query.SysUserCommentInfoQuery;

/**
 * @author adtec
 * @date 2020-07-28 15:38:50
 */
public interface SysUserCommentInfoService extends BaseService<SysUserCommentInfo> {
	SysUserCommentInfoQuery pageByQuery(SysUserCommentInfoQuery query);
	SysUserCommentInfo saveComment(SysUserCommentInfo sysUserCommentInfo);
}
