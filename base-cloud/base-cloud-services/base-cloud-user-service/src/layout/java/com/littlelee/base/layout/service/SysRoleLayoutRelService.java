package com.littlelee.base.layout.service;

import java.util.List;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.layout.model.po.SysRoleLayoutRel;
import com.littlelee.web.antd.bo.TransferNode;

/**
 * @author littlelee
 * @date 2020-08-25 15:06:33
 */
public interface SysRoleLayoutRelService extends BaseService<SysRoleLayoutRel> {

	boolean saveLayoutRel(String layId, List<String> roleIds);

	boolean deleteLayoutRel(String layId, String appId);

	List<TransferNode> listRole(String layId, String appId);

	boolean deleteLayoutRel(String layId, List<String> roleIds);
}
