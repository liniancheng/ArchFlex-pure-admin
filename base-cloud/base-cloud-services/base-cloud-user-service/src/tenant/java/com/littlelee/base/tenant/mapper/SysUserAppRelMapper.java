package com.littlelee.base.tenant.mapper;

import java.util.List;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.tenant.model.bo.TransferVo;
import com.littlelee.base.tenant.model.po.SysUserAppRel;

/**
 * <p>
 * 租户-用户关联表
 * </p>
 * 
 */
public interface SysUserAppRelMapper extends BaseMapper<SysUserAppRel>{
	
	List<TransferVo> getUserAppRels(String appId);

}
