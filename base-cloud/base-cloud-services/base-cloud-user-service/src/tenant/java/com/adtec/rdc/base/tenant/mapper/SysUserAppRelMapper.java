package com.adtec.rdc.base.tenant.mapper;

import java.util.List;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.tenant.model.po.SysUserAppRel;

/**
 * <p>
 * 租户-用户关联表
 * </p>
 * 
 */
public interface SysUserAppRelMapper extends BaseMapper<SysUserAppRel>{
	
	List<TransferVo> getUserAppRels(String appId);

}
