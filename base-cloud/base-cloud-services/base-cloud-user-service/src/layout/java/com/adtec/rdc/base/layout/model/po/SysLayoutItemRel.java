package com.adtec.rdc.base.layout.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 布局-数据项关系表
 * </p>
 *
 * @author dengchf
 * @date 2020-08-25 15:08:26
 */
@Data
@Accessors(chain = true)
public class SysLayoutItemRel implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 布局id
     */
    @TableId(value = "LAY_ID", type = IdType.INPUT)
    private String layId;
    /**
     * 数据项ID
     */
    @TableId(value = "ITEM_ID", type = IdType.INPUT)
    private String itemId;
    /**
     * 列编号
     */
    @TableField(value = "REL_X", strategy = FieldStrategy.IGNORED) 
    private Integer relX;
    /**
     * 行编号
     */
    @TableField(value = "REL_Y", strategy = FieldStrategy.IGNORED) 
    private Integer relY;
    /**
     * 初始宽度
     */
    @TableField(value = "REL_W", strategy = FieldStrategy.IGNORED) 
    private Integer relW;
    /**
     * 初始高度
     */
    @TableField(value = "REL_H", strategy = FieldStrategy.IGNORED) 
    private Integer relH;
    /**
     * 最小高度
     */
    @TableField(exist = false)
    private Integer minH;
    /**
     * 初始宽度
     */
    @TableField(exist = false)
    private Integer minW;
    /**
     * 数据项名称
     */
    @TableField(exist = false)    
    private String itemName;

}