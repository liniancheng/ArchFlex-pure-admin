package com.adtec.rdc.base.layout.mapper;

import com.adtec.rdc.base.layout.model.po.SysRoleLayoutRel;
import com.adtec.rdc.web.antd.bo.TransferNode;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2020-08-25 15:06:33
 */
public interface SysRoleLayoutRelMapper extends BaseMapper<SysRoleLayoutRel> {
	/**
	 * 根据应用id，查询角色列表
	 */
	List<TransferNode> listRole(@Param("appId") String appId, @Param("layId") String layId);

}
