package com.littlelee.base.workflow.model.po;

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
 * 工作流类型信息 
 * </p>
 *
 * @author littlelee
 * @date 2020-06-30 09:13:45
 */
@Data
@Accessors(chain = true)
public class SysWorkflowTypeInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 工作流类型ID
     */
    @TableId(value = "TYPE_ID", type = IdType.ASSIGN_UUID)
    private String typeId;

    /**
     * 工作流类型名称
     */
    @TableField(value = "TYPE_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String typeName;
    
    /**
     * 工作流类型描述
     */
    @TableField(value = "TYPE_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String typeRmk;
    
    /**
     * 租户ID
     */
    @TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String appId;
    
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;
    
    /**
     * 类型排序
     */
    @TableField(value = "TYPE_ORDER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Integer typeOrder;
}