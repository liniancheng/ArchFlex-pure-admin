package com.adtec.rdc.base.dict.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.dict.model.po.SysDictItem;
import com.adtec.rdc.base.dict.model.vo.DictModel;

/**
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
	public List<SysDictItem> selectItemsByDictId(String dictId);
	public String queryDictTextByKey(@Param("code") String code,@Param("key") String key);
	public List<DictModel> queryDictItemsByCode(String code);
	public List<DictModel> queryTableDictItemsByCode(@Param("table") String table,@Param("text") String text,@Param("code") String code);

}
