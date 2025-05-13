package com.adtec.rdc.base.resource.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.resource.model.po.NoticeMailSrvInfo;

/**
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
public interface NoticeMailSrvInfoMapper extends BaseMapper<NoticeMailSrvInfo> {
	/**
	 * 判断是否有同名服务器
	 * @param branchName
	 * @returnv
	 */
	Integer isSameSrvName(@Param("srvName")String srvName,@Param("appId")String appId);
	/**
	 * 判断是否有同名
	 * @param entity
	 * @returnv
	 */
	boolean isExistSrvTitle(NoticeMailSrvInfo entity);
	
	List<NoticeMailSrvInfo> queryMailSrvListByAppId(@Param("appId")String appId);
}
