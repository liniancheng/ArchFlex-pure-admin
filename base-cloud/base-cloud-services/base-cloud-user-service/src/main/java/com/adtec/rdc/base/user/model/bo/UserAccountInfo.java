package com.adtec.rdc.base.user.model.bo;

import lombok.Data;

/**
 * 用户账户信息
 * @author JTao
 *
 */
@Data
public class UserAccountInfo {
	/**
     * 主键ID
     */
    private String userId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 手机号码
     */
    private String userMobTel;
    /**
     * 角色
     */
    private String roleNames;
    /**
     * 部门
     */
    private String branchName;
    /**
     * 上次登录时间
     */
    private String lastLoginTime;
}
