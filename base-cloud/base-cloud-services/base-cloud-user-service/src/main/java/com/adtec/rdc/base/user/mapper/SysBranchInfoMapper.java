package com.adtec.rdc.base.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;
import com.adtec.rdc.base.user.model.query.SysBranchQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author: Liushp
 * @since 2019-11-27
 */
public interface SysBranchInfoMapper extends BaseMapper<SysBranchInfo> {

	/**
	 * 判断是否含有子机构
	 * @param branchNo
	 * @return 
	 */
	Boolean hasChildren(String branchNo);
	
	/**
	 * 批量插入机构信息
	 * @param branchs
	 * @return
	 */
	void batchInsert(List<SysBranchInfo> branchs);
	
	/**
	 * 判断是否有同名机构
	 * @param branchName
	 * @return
	 */
	Integer isSameBranchName(String branchName);
	
	/**
	 * 判断是否有相同机构号
	 * @param branchNo
	 * @return
	 */
	Integer isSameBranchNo(String branchNo);
	
	/**
	 * 更新判断方法
	 * @param branchName
	 * @param branchNo
	 * @return
	 */
	Integer isSameBranchNoAndName(String branchName, String branchNo);
	
	
	IPage<SysBranchInfo> getPageForBranch(SysBranchQuery query);
	
	/**
	 * 根据机构号与机构类型查询机构
	 * @param branchNo
	 * @param branchType
	 * @return
	 */
	SysBranchTree selectByBranchNoAndBranchType(String branchNo, String branchType);

	List<SysBranchInfo> loadAllBranchByAppIdAndBranchType(@Param("appId")String appId, @Param("branchType")String branchType);
}
