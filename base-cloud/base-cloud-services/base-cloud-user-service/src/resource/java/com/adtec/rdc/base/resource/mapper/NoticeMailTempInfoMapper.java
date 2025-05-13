package com.adtec.rdc.base.resource.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.resource.model.po.NoticeMailTempInfo;

/**
 * @author xuzhh
 * @date 2019-11-29 10:29:11
 */
public interface NoticeMailTempInfoMapper extends BaseMapper<NoticeMailTempInfo> {
	/**
	 * 判断是否有同名模板名称
	 * @param branchName
	 * @return
	 */
	Integer isSameTempName(@Param("tempName")String tempName,@Param("appId")String appId);
	/**
	 * 判断是否有同名
	 * @param entity
	 * @return
	 */
	boolean isExistSrvTitle(NoticeMailTempInfo entity);
	
	List<NoticeMailTempInfo> queryMailTempListByAppId(@Param("appId")String appId);
}
