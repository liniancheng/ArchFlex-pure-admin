package com.littlelee.base.dict.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.dict.model.po.SysDictItem;
import com.littlelee.base.dict.model.query.SysDictItemQuery;
import com.littlelee.base.dict.model.vo.DictModel;

/**
 * @author littlelee
 * @date 2020-06-28 07:49:50
 */
public interface SysDictItemService extends BaseService<SysDictItem> {
	SysDictItemQuery pageByQuery(SysDictItemQuery query);
	public List<SysDictItem> selectItemsByDictId(String dictId);
	public List<DictModel> queryDictItemsByCode(String code);
	public List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

	List<SysDictItem> getByDictCode(String dictCode);
}
