package com.adtec.rdc.base.knowledge.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 知识分类表
 * </p>
 *
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@Data
@Accessors(chain = true)
public class KnowKnowledgeDirInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    @TableId(value = "DIR_ID", type = IdType.UUID)
    private String dirId;

    /**
     * 分类名称
     */
    @TableField(value = "DIR_NAME", strategy = FieldStrategy.IGNORED) 
    private String dirName;
    /**
     * 父ID
     */
    @TableField(value = "PARENT_ID", strategy = FieldStrategy.IGNORED) 
    private String parentId;
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED) 
    private String appId;
    /**
     * 分类描述
     */
    @TableField(value = "DIR_RMK", strategy = FieldStrategy.IGNORED) 
    private String dirRmk;
    /**
     * 排序
     */
    @TableField(value = "DIR_ORDER", strategy = FieldStrategy.IGNORED) 
    private Integer dirOrder;

}