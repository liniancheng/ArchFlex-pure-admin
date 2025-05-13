package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SysMenuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "menu_id", type = IdType.UUID)
    private String menuId;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
    private String appId;
    
    /**
     * 资源名称
     */
    @TableField(value = "menu_name", strategy = FieldStrategy.IGNORED)
    private String menuName;

    /**
     * 路径名称
     */
    @TableField(value = "menu_route_name", strategy = FieldStrategy.IGNORED)
    private String menuRouteName;

    /**
     * 资源类型 0-菜单 1-按钮
     */
    @TableField(value = "menu_type", strategy = FieldStrategy.IGNORED)
    private String menuType;

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
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "modify_time", strategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;

    /**
     * 是否删除 1-删除，0-未删除
     */
    @TableField(value = "del_flag", strategy = FieldStrategy.IGNORED)
    private String delFlag;

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
     * 	需要权限 0：否；1：是
     */
    @TableField(value = "auth_flag", strategy = FieldStrategy.IGNORED)
    private String authFlag;
    
    /**
     * 	隐藏菜单	0：否；1：是
     */
    @TableField(value = "hide_flag", strategy = FieldStrategy.IGNORED)
    private String hideFlag;
    
    /**
     * 	缓存	0：否；1：是
     */
    @TableField(value = "alive_flag", strategy = FieldStrategy.IGNORED)
    private String aliveFlag;

}
