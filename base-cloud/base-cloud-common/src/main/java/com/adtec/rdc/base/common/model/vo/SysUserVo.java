package com.adtec.rdc.base.common.model.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author: JTao
 * @date: 2018/10/16 17:09
 * @description:
 */
@Data
public class SysUserVo {

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
     * 密码
     */
    private String loginPwd;

    /**
     * 邮箱
     */
    private String userEmail;

    /**
     * 手机号码
     */
    private String userMobTel;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 是否删除 0-未删除 1-删除
     */
    private String delFlag;

    private List<SysRoleVo> sysRoleVoList;
    /**
     * 机构号，配合用户组实现主从机构
     */
    private String branchNo;
    /**
     * 内部加密密码
     */
    private String cryptPwd;
    /**
     * 机构名称
     */
    private String branchName;
    /**
     * 
     */
    private String appId;
    
    /**
     *   1 与登录名相同 
     *   2 登录名+固定密码 
     *  3 固定密码
     */
    private String passWordType;
    
    /**
     * 用户登录时间
     */
    private Date loginTime;
    /**
     * 密码即将到期倒计时（单位：天）
     */
    private int countdown;
}
