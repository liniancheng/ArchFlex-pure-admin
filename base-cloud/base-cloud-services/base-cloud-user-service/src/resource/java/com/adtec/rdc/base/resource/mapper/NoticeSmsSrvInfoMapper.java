package com.adtec.rdc.base.resource.mapper;

import com.adtec.rdc.base.resource.model.po.NoticeSmsSrvInfo;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author adtec
 * @date 2020-06-16 13:00:26
 */
public interface NoticeSmsSrvInfoMapper extends BaseMapper<NoticeSmsSrvInfo> {
	/**
	 * 判断是否有同名服务器
	 * @param srvName
	 * @return
	 */
	Integer isSameSrvName(@Param("srvName")String srvName,@Param("appId")String appId);
	/**
	 *  判断是否有重复
	 * @param entity
	 * @return
	 */
	boolean isExistSmsTitle(NoticeSmsSrvInfo entity);
}
