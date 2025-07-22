package com.littlelee.base.resource.mapper;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;
import com.littlelee.base.resource.model.po.NoticeSmsTempInfo;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
public interface NoticeSmsTempInfoMapper extends BaseMapper<NoticeSmsTempInfo> {
	/**
	 * 判断是否有同名模板名称
	 * @param branchName
	 * @return
	 */
	Integer isSameTempName(@Param("tempName")String tempName,@Param("appId")String appId);
	/**
	 *  判断是否有重复
	 * @param entity
	 * @return
	 */
	boolean isExistSmsTitle(NoticeSmsTempInfo entity);
}
