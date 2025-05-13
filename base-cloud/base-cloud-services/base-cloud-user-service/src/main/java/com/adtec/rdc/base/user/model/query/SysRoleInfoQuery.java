package com.adtec.rdc.base.user.model.query;

import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author: JTao
 * @date: 2018/11/1 17:20
 */
@Data
public class SysRoleInfoQuery extends Page<SysRoleInfo> {
	
	private static final long serialVersionUID = 1L;

    /**
     * 角色code用于springsecurity角色标识码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;
    
    /**
     * 租户ID
     */
    private String appId;
    /**
     * 删除状态
     */
    private String delFlag;
    
    /**
     * 角色id
     */
    private String roleId;
    
    /**
     * 用户id 获取用户关联角色信息使用
     */
    private String userId;
    
}
