package com.littlelee.base.layout.mapper;

import com.littlelee.base.layout.model.po.SysRoleLayoutItemRel;
import com.littlelee.web.antd.bo.TransferNode;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.littlelee.base.common.base.mapper.BaseMapper;

/**
 * @author littlelee
 * @date 2020-08-25 15:05:27
 */
public interface SysRoleLayoutItemRelMapper extends BaseMapper<SysRoleLayoutItemRel> {

	List<TransferNode> listRole(@Param("appId") String appId, @Param("itemId") String itemId);

//	void batchInsert(@Param("list") List<SysRoleLayoutItemRel> list);

}
