package com.littlelee.base.user.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.user.model.po.SysUserCommentInfo;
import com.littlelee.base.user.model.query.SysUserCommentInfoQuery;

/**
 * @author littlelee
 * @date 2020-07-28 15:38:50
 */
public interface SysUserCommentInfoService extends BaseService<SysUserCommentInfo> {
	SysUserCommentInfoQuery pageByQuery(SysUserCommentInfoQuery query);
	SysUserCommentInfo saveComment(SysUserCommentInfo sysUserCommentInfo);
}
