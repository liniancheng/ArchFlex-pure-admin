package com.adtec.rdc.base.tenant.model.po;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单模板表
 * </p>
 *
 * @author lsp
 * @date 2020-05-14 14:51:04
 */
@Data
@Accessors(chain = true)
public class SysMenuTmp implements Serializable {
   
	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "menu_id", type = IdType.UUID)
    private String menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name", strategy = FieldStrategy.IGNORED) 
    private String menuName;
    /**
     * 菜单路径名称,用于多语言标识
     */
    @TableField(value = "menu_route_name", strategy = FieldStrategy.IGNORED) 
    private String menuRouteName;
    /**
     * 菜单类型 0-菜单 1-按钮
     */
    @TableField(value = "menu_type", strategy = FieldStrategy.IGNORED) 
    private String menuType;
    /**
     * 后台请求url
     */
    @TableField(value = "menu_url", strategy = FieldStrategy.IGNORED) 
    private String menuUrl;
    /**
     * 请求方式
     */
    @TableField(value = "http_method", strategy = FieldStrategy.IGNORED) 
    private String httpMethod;
    /**
     * 前端url
     */
    @TableField(value = "menu_path", strategy = FieldStrategy.IGNORED) 
    private String menuPath;
    /**
     * 按钮权限资源标识
     */
    @TableField(value = "button_permission", strategy = FieldStrategy.IGNORED) 
    private String buttonPermission;
    /**
     * 颜色
     */
    @TableField(value = "menu_color", strategy = FieldStrategy.IGNORED) 
    private String menuColor;
    /**
     * 父资源id
     */
    @TableField(value = "parent_id", strategy = FieldStrategy.IGNORED) 
    private String parentId;
    /**
     * 图标
     */
    @TableField(value = "menu_icon", strategy = FieldStrategy.IGNORED) 
    private String menuIcon;
    /**
     * 组件路径
     */
    @TableField(value = "component_path", strategy = FieldStrategy.IGNORED) 
    private String componentPath;
    /**
     * 排序权重
     */
    @TableField(value = "menu_sort", strategy = FieldStrategy.IGNORED) 
    private Integer menuSort;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", strategy = FieldStrategy.IGNORED) 
    private Date createTime;
    /**
     * 更新时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED) 
    private Date modifyTime;
    /**
     * 是否删除 1-删除，0-未删除
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED) 
    private String delFlag;
    /**
     * 租户id
     */
    @TableField(value = "app_id", strategy = FieldStrategy.IGNORED) 
    private String appId;
    /**
     * 管理端标识 1-管理端，0-业务端
     */
    @TableField(value = "app_flag", strategy = FieldStrategy.IGNORED) 
    private String appFlag;
    
}