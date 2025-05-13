package com.adtec.rdc.base.param.mapper;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.param.model.po.SysParamInfo;

public interface SysParamInfoMapper extends BaseMapper<SysParamInfo>{
	
	boolean isExistParamName(SysParamInfo query);

}
