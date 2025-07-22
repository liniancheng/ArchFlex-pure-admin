package com.littlelee.base.dict.mapper;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.dict.model.po.SysDict;

/**
 * @author littlelee
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
