package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.user.model.po.SysResInfo;
import com.adtec.rdc.base.user.model.query.SysResInfoQuery;

import java.util.List;

/**
 * @author adtec
 * @date 2022-03-29 14:14:04
 */
public interface SysResInfoService extends BaseService<SysResInfo> {
	SysResInfoQuery pageByQuery(SysResInfoQuery query);

    List<SysResInfo> fetchList();

    Boolean test(SysResInfo resall);
}
