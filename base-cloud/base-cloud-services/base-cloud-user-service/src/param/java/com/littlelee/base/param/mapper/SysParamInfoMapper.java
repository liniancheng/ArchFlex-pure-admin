package com.littlelee.base.param.mapper;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.param.model.po.SysParamInfo;

public interface SysParamInfoMapper extends BaseMapper<SysParamInfo>{
	
	boolean isExistParamName(SysParamInfo query);

}
