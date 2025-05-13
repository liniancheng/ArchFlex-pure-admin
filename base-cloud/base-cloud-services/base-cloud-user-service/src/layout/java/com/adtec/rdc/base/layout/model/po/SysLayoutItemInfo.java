package com.adtec.rdc.base.layout.model.po;

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
 * 布局数据项信息表
 * </p>
 *
 * @author dengchf
 * @date 2020-08-25 15:17:06
 */
@Data
@Accessors(chain = true)
public class SysLayoutItemInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ITEM_ID", type = IdType.UUID)
    private String itemId;

    /**
     * 布局名称
     */
    @TableField(value = "ITEM_NAME", strategy = FieldStrategy.IGNORED) 
    private String itemName;
    /**
     * 应用ID
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED) 
    private String appId;
    /**
     * 组件路径
     */
    @TableField(value = "ITEM_COMP", strategy = FieldStrategy.IGNORED) 
    private String itemComp;
    /**
     * 最小宽度
     */
    @TableField(value = "MIN_WIDTH", strategy = FieldStrategy.IGNORED) 
    private Integer minWidth;
    /**
     * 最小高度
     */
    @TableField(value = "MIN_HEIGHT", strategy = FieldStrategy.IGNORED) 
    private Integer minHeight;
    /**
     * 登录名
     */
    @TableField(value = "LOGIN_NAME", strategy = FieldStrategy.IGNORED) 
    private String loginName;
    /**
     * 描述
     */
    @TableField(value = "ITEM_RMK", strategy = FieldStrategy.IGNORED) 
    private String itemRmk;
    /**
     * 数据项排序
     */
    @TableField(value = "ITEM_ORDER", strategy = FieldStrategy.IGNORED) 
    private Integer itemOrder;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED) 
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED) 
    private LocalDateTime modifyTime;
    /**
     * 布局id
     */
    @TableField(exist = false)
    private String layId;    
    /**
     * 列编号
     */
    @TableField(exist = false)
    private Integer relX;
    /**
     * 行编号
     */
    @TableField(exist = false)
    private Integer relY;
    /**
     * 初始宽度
     */
    @TableField(exist = false)
    private Integer relW;
    /**
     * 初始高度
     */
    @TableField(exist = false)
    private Integer relH;

}