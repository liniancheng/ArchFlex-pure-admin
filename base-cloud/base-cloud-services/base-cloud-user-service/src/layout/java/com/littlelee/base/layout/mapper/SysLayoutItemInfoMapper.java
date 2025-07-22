package com.littlelee.base.layout.mapper;

import com.littlelee.base.layout.model.po.SysLayoutItemInfo;
import com.littlelee.base.layout.model.query.SysLayoutItemInfoQuery;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2020-08-25 15:17:06
 */
public interface SysLayoutItemInfoMapper extends BaseMapper<SysLayoutItemInfo> {

	boolean isExist(SysLayoutItemInfo entity);

	List<SysLayoutItemInfo> listItems(@Param("layId") String id, @Param("appId") String appId);

	SysLayoutItemInfoQuery fetchPerson(SysLayoutItemInfoQuery query);

}
