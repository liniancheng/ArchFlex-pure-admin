package com.littlelee.base.dict.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.dict.model.po.SysDict;
import com.littlelee.base.dict.model.query.SysDictQuery;

/**
 * @author littlelee
 * @date 2020-06-28 07:42:28
 */
public interface SysDictService extends BaseService<SysDict> {
	SysDictQuery pageByQuery(SysDictQuery query);
}
