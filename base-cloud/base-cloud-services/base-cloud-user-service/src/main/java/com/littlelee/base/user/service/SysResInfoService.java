package com.littlelee.base.user.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.user.model.po.SysResInfo;
import com.littlelee.base.user.model.query.SysResInfoQuery;

import java.util.List;

/**
 * @author littlelee
 * @date 2022-03-29 14:14:04
 */
public interface SysResInfoService extends BaseService<SysResInfo> {
	SysResInfoQuery pageByQuery(SysResInfoQuery query);

    List<SysResInfo> fetchList();

    Boolean test(SysResInfo resall);
}
