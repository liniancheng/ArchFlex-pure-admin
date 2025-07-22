package com.littlelee.base.layout.mapper;

import com.littlelee.base.layout.model.po.SysLayoutInfo;
import com.littlelee.base.layout.model.query.SysLayoutInfoQuery;
import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2020-08-25 15:04:09
 */
public interface SysLayoutInfoMapper extends BaseMapper<SysLayoutInfo> {

	boolean isExistName(SysLayoutInfo entity);

	boolean isExistLevel(SysLayoutInfo entity);
	
	SysLayoutInfoQuery queryOne(SysLayoutInfoQuery query);

}
