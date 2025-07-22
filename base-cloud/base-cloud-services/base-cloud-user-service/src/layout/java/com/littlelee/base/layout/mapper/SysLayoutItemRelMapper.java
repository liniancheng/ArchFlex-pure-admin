package com.littlelee.base.layout.mapper;

import com.littlelee.base.layout.model.po.SysLayoutItemRel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2020-08-25 15:08:26
 */
public interface SysLayoutItemRelMapper extends BaseMapper<SysLayoutItemRel> {

	boolean isExist(SysLayoutItemRel entity);

	List<SysLayoutItemRel> listRels(@Param("layId") String id, @Param("appId") String appId);

}
