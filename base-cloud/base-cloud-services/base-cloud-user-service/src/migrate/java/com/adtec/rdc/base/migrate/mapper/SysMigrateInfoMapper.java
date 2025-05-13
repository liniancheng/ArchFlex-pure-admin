package com.adtec.rdc.base.migrate.mapper;

import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;

import java.util.List;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
public interface SysMigrateInfoMapper extends BaseMapper<SysMigrateInfo> {
	/**
	 * 查询全部
	 * @return
	 */
	List<SysMigrateInfo> findAll();

}
