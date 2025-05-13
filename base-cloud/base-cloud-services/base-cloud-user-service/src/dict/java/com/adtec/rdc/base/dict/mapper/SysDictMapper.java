package com.adtec.rdc.base.dict.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.dict.model.po.SysDict;

/**
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
public interface SysDictMapper extends BaseMapper<SysDict> {
	/**
	 * 判断是否有相同字典编码
	 * @param branchName
	 * @return
	 */
	Integer isSameDictCode(String dictCode);
	
//	SysDict queryDictById(String id);

}
