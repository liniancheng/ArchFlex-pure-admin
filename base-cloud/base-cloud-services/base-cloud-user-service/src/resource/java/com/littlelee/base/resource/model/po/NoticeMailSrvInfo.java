package com.littlelee.base.resource.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 邮件服务器信息表
 * </p>
 *
 * @author xuzhh
 * @date 2019-11-29 10:31:17
 */
@Data
@Accessors(chain = true)
public class NoticeMailSrvInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务器标识
     */
    @TableId(value = "SRV_ID", type = IdType.ASSIGN_UUID)
    private String srvId;

    /**
     * 服务器名称
     */
    @TableField(value = "SRV_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String srvName;
    /**
     * 服务器描述
     */
    @TableField(value = "SRV_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String srvRmk;
    /**
     * 用户名
     */
    @TableField(value = "LOGIN_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String loginName;
    /**
     * 密码
     */
    @TableField(value = "LOGIN_PWD", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String loginPwd;
    /**
     * 邮件服务器地址
     */
    @TableField(value = "SRV_URL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String srvUrl;
    /**
     * 邮箱地址
     */
    @TableField(value = "DEFAULT_FROM", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String defaultFrom;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED, fill = FieldFill.UPDATE)
    private LocalDateTime modifyTime;
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String appId;
    /**
     * 显示名称
     */
    @TableField(value = "SHOW_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String showName;
    /**
     * 邮件服务器端口
     */
    @TableField(value = "SRV_PORT", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private int srvPort;
    /**
     * SSL标志
     */
    @TableField(value = "SSL_FLAG", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sslFlag;
}