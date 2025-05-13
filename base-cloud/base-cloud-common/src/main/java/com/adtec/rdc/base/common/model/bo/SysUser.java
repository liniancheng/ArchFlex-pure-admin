package com.adtec.rdc.base.common.model.bo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
    /**
     * 内部加密密码
     */
    private String cryptPwd;
    /**
     * 机构编号
     */
    private String branchNo;
}
