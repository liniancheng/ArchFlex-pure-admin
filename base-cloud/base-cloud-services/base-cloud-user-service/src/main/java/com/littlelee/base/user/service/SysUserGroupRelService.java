package com.littlelee.base.user.service;

import java.util.List;

import com.littlelee.base.tenant.model.bo.TransferVo;
import com.littlelee.base.user.model.po.SysUserGroupRel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserGroupRelService extends IService<SysUserGroupRel>  {

	public Boolean saveGroupRel(String groupId, List<String> userIds, String appId);

	public Boolean deleteUserGroupRel(String groupId, List<String> userIds);

	public List<TransferVo> getUserList(String groupId);

}
