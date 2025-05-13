package com.adtec.rdc.base.resource.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.model.vo.SysDblinkVo;
import com.adtec.rdc.base.resource.model.po.SysDblinkInfo;
import com.adtec.rdc.base.resource.model.query.SysDblinkInfoQuery;

/**
 * @author xuzhh
 * @date 2019-11-22 14:07:28
 */
public interface SysDblinkInfoService extends BaseService<SysDblinkInfo> {

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	SysDblinkInfoQuery pageByQuery(SysDblinkInfoQuery query);

	/**
	 *
	 * 测试数据源是否可用
	 * 
	 * @author dengchf
	 */
	Boolean test(String id);

	/**
	 * 
	 * 判断是否重名
	 * 
	 * @author dengchf
	 */
	Boolean isExistName(SysDblinkInfo info);
	/**
	 * 数据源列表
	 *
	 * @author dengchf
	 */
	List<TreeNode> listDb();
	/**
	 * @author dengchf
	 * @description 获取数据源信息
	 */
	SysDblinkVo getVoById(String id);
	/**
	 * 查询所有可用数据源
	 *
	 * @author dengchf
	 */
	List<SysDblinkVo> listDbVo();
}
