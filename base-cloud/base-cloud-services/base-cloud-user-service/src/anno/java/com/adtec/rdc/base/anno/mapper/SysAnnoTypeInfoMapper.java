package com.adtec.rdc.base.anno.mapper;

import com.adtec.rdc.base.anno.model.po.SysAnnoTypeInfo;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
public interface SysAnnoTypeInfoMapper extends BaseMapper<SysAnnoTypeInfo> {
	
	boolean isExistTypeName(SysAnnoTypeInfo query);

}
