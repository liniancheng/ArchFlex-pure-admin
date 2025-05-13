package com.adtec.rdc.base.tenant.mapper;

import java.util.List;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.tenant.model.po.SysMenuTmp;

/**
 * @author lsp
 * @date 2020-05-14 14:51:04
 */
public interface SysMenuTmpMapper extends BaseMapper<SysMenuTmp> {

	
	List<SysMenuTmp> queryMenuTmps(String appFlag);
}
