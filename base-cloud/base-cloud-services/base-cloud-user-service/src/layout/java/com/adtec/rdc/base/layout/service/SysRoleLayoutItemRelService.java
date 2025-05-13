package com.adtec.rdc.base.layout.service;

import java.util.List;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.layout.model.po.SysRoleLayoutItemRel;
import com.adtec.rdc.web.antd.bo.TransferNode;

/**
 * @author dengchf
 * @date 2020-08-25 15:05:27
 */
public interface SysRoleLayoutItemRelService extends BaseService<SysRoleLayoutItemRel> {

	boolean saveItemRel(String itemId, List<String> roleIds);

	boolean deleteItemRel(String itemId, String appId);

	List<TransferNode> listRole(String itemId, String appId);

	boolean deleteItemRel(String itemId, List<String> roleIds);
}
