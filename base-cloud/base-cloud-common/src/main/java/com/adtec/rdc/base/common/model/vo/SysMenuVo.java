package com.adtec.rdc.base.common.model.vo;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源表(菜单与按钮)
 * </p>
 *
 * @author: JTao
 * @since 2018-10-16
 */
@Data
@Accessors(chain = true)
public class SysMenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    
    /**
     * 租户id
     */
    private String appId;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 路径名称
     */
    private String routeName;

    /**
     * 资源类型 0-菜单 1-按钮
     */
    private String type;

    /**
     * 前端url
     */
    private String path;

    /**
     * 按钮权限资源标识
     */
    private String permission;

    /**
     * 颜色
     */
    private String color;

    /**
     * 父资源id
     */
    private String parentId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 排序权重
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime modifyTime;

    /**
     * 是否删除 1-删除，0-未删除
     */
    private String delFlag;

    /**
     * 后台请求url
     */
    private String url;

    /**
     * 请求方式
     */
    private String method;
    /**
     * 需要赋权
     */
    private String authFlag;
    /**
     * 隐藏菜单
     */
    private String hideFlag;
    /**
     * 缓存标识
     */
    private String aliveFlag;

}
