package com.adtec.rdc.base.tenant.model.bo;

import java.util.List;

import com.adtec.rdc.base.user.model.bo.SysMenuTree;

import lombok.Data;

/**
 * 租户树
 */
@Data
public class SysAppTree {

    /**
     * 租户id
     */
    private String appId;

    /**
     * 租户logo路径
     */
    private String appLogo;
    /**
     * 租户名称
     */
    private String appName;
    /**
     * 租户排序
     */
    private Integer appOrder;
    /**
     * 关联租户
     */
    private String appRel;
    /**
     * 租户描述
     */
    private String appRmk;
    /**
     * 租户状态
     */
    private String appState;
    /**
     * 租户地址
     */
    private String appUrl;
    /**
     * 浏览器标题
     */
    private String browserTitle;

    /**
     * 业务
     */
    private List<SysAppTree> children;
    
    public void addChildren(SysAppTree tree) {
        this.children.add(tree);
    }
}
