package com.adtec.rdc.base.layout.mapper;

import com.adtec.rdc.base.layout.model.po.SysRoleLayoutItemRel;
import com.adtec.rdc.web.antd.bo.TransferNode;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.adtec.rdc.base.common.base.mapper.BaseMapper;

/**
 * @author dengchf
 * @date 2020-08-25 15:05:27
 */
public interface SysRoleLayoutItemRelMapper extends BaseMapper<SysRoleLayoutItemRel> {

	List<TransferNode> listRole(@Param("appId") String appId, @Param("itemId") String itemId);

//	void batchInsert(@Param("list") List<SysRoleLayoutItemRel> list);

}
