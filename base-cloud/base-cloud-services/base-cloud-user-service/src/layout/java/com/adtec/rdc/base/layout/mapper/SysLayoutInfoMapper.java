package com.adtec.rdc.base.layout.mapper;

import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.adtec.rdc.base.layout.model.query.SysLayoutInfoQuery;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2020-08-25 15:04:09
 */
public interface SysLayoutInfoMapper extends BaseMapper<SysLayoutInfo> {

	boolean isExistName(SysLayoutInfo entity);

	boolean isExistLevel(SysLayoutInfo entity);
	
	SysLayoutInfoQuery queryOne(SysLayoutInfoQuery query);

}
