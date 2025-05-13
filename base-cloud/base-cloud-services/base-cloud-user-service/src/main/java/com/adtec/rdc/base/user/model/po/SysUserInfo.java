package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author: JTao
 * @since 2018-10-08
 */
@Data
@Accessors(chain = true)
public class SysUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "user_id", type = IdType.UUID)
    private String userId;
    
    /**
     * 登录名
     */
    @TableField(value = "login_name", strategy = FieldStrategy.IGNORED)
    private String loginName;
    
    /**
     * 租户id
     */
    @TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
    private String appId;

    /**
     * 用户名
     */
    @TableField(value = "user_name", strategy = FieldStrategy.IGNORED)
    private String userName;

    /**
     * 密码
     */
    @TableField(value = "login_pwd", strategy = FieldStrategy.IGNORED)
    private String loginPwd;

    /**
     * 邮箱
     */
    @TableField(value = "user_email", strategy = FieldStrategy.IGNORED)
    private String userEmail;

    /**
     * 手机号码
     */
    @TableField(value = "user_mob_tel", strategy = FieldStrategy.IGNORED)
    private String userMobTel;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;
    
    /**
     *登录时间
     */
    @TableField(value = "login_time", strategy = FieldStrategy.IGNORED)
    private Date loginTime;

    /**
     * 是否删除 0-未删除 1-删除
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED)
    private String delFlag;
    
    @TableField(exist = false)
    private String userAvatar;
    
    @TableField(exist = false)
    private List<String> roles;//roleCodes

    @TableField(exist = false)
    private List<String> permissions;
    /**
     * 主机构号，配合用户组实现主从机构
     */
    @TableField(value = "branch_no", strategy = FieldStrategy.IGNORED)
    private String branchNo;
    /**
     * 内部加密字段，用于用户同步等操作
     */
    @TableField(value = "crypt_pwd", strategy = FieldStrategy.IGNORED)
    private String cryptPwd;
    /**
     * 最近一次登录时间
     */
    @TableField(exist = false)
    private String lastLoginTime;
    /**
     * 机构名称
     */
    @TableField(exist = false)
    private String branchName; 
    /**
     * 角色名称
     */
    @TableField(exist = false)
    private String roleNames;
    /**
     * 最近一次修改密码时间
     */
    @TableField(exist = false)
    private LocalDateTime lastModifyTime;

	@Override
	public String toString() {
		return "SysUserInfo [userId=" + userId + ", loginName=" + loginName + ", userName=" + userName + ", loginPwd="
				+ loginPwd + ", userEmail=" + userEmail + ", userMobTel=" + userMobTel + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + ", delFlag=" + delFlag + ", userAvatar=" + userAvatar + "]";
	}
}
