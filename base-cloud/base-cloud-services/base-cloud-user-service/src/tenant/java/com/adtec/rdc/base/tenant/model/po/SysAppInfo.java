package com.adtec.rdc.base.tenant.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 租户信息表
 * </p>
 *
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@Data
@Accessors(chain = true)
public class SysAppInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @TableId(value = "app_id", type = IdType.UUID)
    private String appId;

    /**
     * 租户logo路径
     */
    @TableField(value = "app_logo", strategy = FieldStrategy.IGNORED) 
    private String appLogo;
    /**
     * 租户名称
     */
    @TableField(value = "app_name", strategy = FieldStrategy.IGNORED) 
    private String appName;
    /**
     * 租户排序
     */
    @TableField(value = "app_order", strategy = FieldStrategy.IGNORED) 
    private Integer appOrder;
    /**
     * 关联租户
     */
    @TableField(value = "app_rel", strategy = FieldStrategy.IGNORED) 
    private String appRel;
    /**
     * 租户描述
     */
    @TableField(value = "app_rmk", strategy = FieldStrategy.IGNORED) 
    private String appRmk;
    /**
     * 租户状态
     */
    @TableField(value = "app_state", strategy = FieldStrategy.IGNORED) 
    private String appState;
    /**
     * 租户地址
     */
    @TableField(value = "app_url", strategy = FieldStrategy.IGNORED) 
    private String appUrl;
    /**
     * 浏览器标题
     */
    @TableField(value = "browser_title", strategy = FieldStrategy.IGNORED) 
    private String browserTitle;

}