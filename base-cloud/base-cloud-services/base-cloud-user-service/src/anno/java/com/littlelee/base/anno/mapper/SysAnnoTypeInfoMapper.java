package com.littlelee.base.anno.mapper;

import com.littlelee.base.anno.model.po.SysAnnoTypeInfo;
import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2019-11-26 09:51:11
 */
public interface SysAnnoTypeInfoMapper extends BaseMapper<SysAnnoTypeInfo> {
	
	boolean isExistTypeName(SysAnnoTypeInfo query);

}
