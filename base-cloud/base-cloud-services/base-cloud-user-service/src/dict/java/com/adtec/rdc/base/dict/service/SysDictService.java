package com.adtec.rdc.base.dict.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.dict.model.po.SysDict;
import com.adtec.rdc.base.dict.model.query.SysDictQuery;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
public interface SysDictService extends BaseService<SysDict> {
	SysDictQuery pageByQuery(SysDictQuery query);
}
