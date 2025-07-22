package com.littlelee.base.migrate.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.migrate.model.bo.MigrateExport;
import com.littlelee.base.migrate.model.po.SysMigrateInfo;

/**
 * @author littlelee
 * @date 2019-12-15 22:22:24
 */
public interface SysMigratePubService extends BaseService<SysMigrateInfo> {
	
	/**
	 * 按照选中的节点导出数据
	 * @param ids
	 * @return
	 */
	MigrateExport downLoad(List<String> ids);
	/**
	 * 导入数据
	 * @return
	 */
	Boolean upload(MigrateExport migrateExport);
	/**
	 * 获取资源列表
	 * @return
	 */
	List<TreeNode> getAllTreeNodes();

}
