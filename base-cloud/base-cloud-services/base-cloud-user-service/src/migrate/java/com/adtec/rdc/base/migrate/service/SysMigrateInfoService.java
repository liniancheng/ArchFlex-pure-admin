package com.adtec.rdc.base.migrate.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.migrate.model.bo.SysMigrateTree;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;
import com.adtec.rdc.base.migrate.model.query.SysMigrateInfoQuery;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
public interface SysMigrateInfoService extends BaseService<SysMigrateInfo> {
	SysMigrateInfoQuery pageByQuery(SysMigrateInfoQuery query);

	/**
	 * 按parentId获取资源节点
	 * 
	 * @param parentId
	 * @return
	 */
	List<SysMigrateTree> getTreeNodes(String pId);

	/**
	 * 查询资源树全部节点
	 * 
	 * @return
	 */
	List<SysMigrateTree> getAllTreeNodes();

	/**
	 * 保存
	 * @param migrate
	 * @return
	 */
	Boolean onlySave(SysMigrateInfo migrate);
	/**
	 * 更新
	 * @param migrate
	 * @return
	 */
	Boolean onlyUpdateById(SysMigrateInfo migrate);

}
