package com.adtec.rdc.base.user.service;

import java.util.List;

import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.user.model.po.SysUserGroupRel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysUserGroupRelService extends IService<SysUserGroupRel>  {

	public Boolean saveGroupRel(String groupId, List<String> userIds, String appId);

	public Boolean deleteUserGroupRel(String groupId, List<String> userIds);

	public List<TransferVo> getUserList(String groupId);

}
