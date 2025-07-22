package com.littlelee.base.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.dict.model.po.SysDictItem;
import com.littlelee.base.dict.model.vo.DictModel;

/**
 * @author littlelee
 * @date 2020-06-28 07:49:50
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
	public List<SysDictItem> selectItemsByDictId(String dictId);
	public String queryDictTextByKey(@Param("code") String code,@Param("key") String key);
	public List<DictModel> queryDictItemsByCode(String code);
	public List<DictModel> queryTableDictItemsByCode(@Param("table") String table,@Param("text") String text,@Param("code") String code);

}
