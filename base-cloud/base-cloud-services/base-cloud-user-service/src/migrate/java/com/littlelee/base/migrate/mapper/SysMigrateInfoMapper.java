package com.littlelee.base.migrate.mapper;

import com.littlelee.base.migrate.model.po.SysMigrateInfo;

import java.util.List;

import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2019-12-15 22:22:24
 */
public interface SysMigrateInfoMapper extends BaseMapper<SysMigrateInfo> {
	/**
	 * 查询全部
	 * @return
	 */
	List<SysMigrateInfo> findAll();

}
