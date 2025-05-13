package com.adtec.rdc.base.user.service;

import java.io.InputStream;
import java.util.List;

import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;
import com.adtec.rdc.base.user.model.query.SysBranchQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author liushp
 * @date 2019-11-27 16:11:47
 * @description: 系统机构服务类
 */
public interface SysBranchInfoService extends IService<SysBranchInfo> {

    /**
     * 查询所有的机构
     * @return
     */
    List<SysBranchTree> getAllBranchTree();

    /**
     * 查询所有的子机构
     * @return
     */
    List<TreeNode> getAllBranchTreeNode();

    /**
     * 获取全部的机构
     * @return
     */
    List<SysBranchInfo> getAllBranchs();
    /**
     * 批量导入机构
     */
	String importBranch(InputStream inputStream);

	/**
	 * 查询指定类型机构树
	 * @author dengchf
	 * @data 2020年3月25日
	 */
	List<TreeNode> treeBranchNosByType(String type);

	/**
	 * 分页查询机构
	 * @param query
	 * @return
	 */
	SysBranchQuery getAllBranchTreeByPage(SysBranchQuery query);

    List<SysBranchInfo> fetchBranchs();

    List<SysBranchInfoVo> selectBatchByIds(List<String> orgVals);
}
