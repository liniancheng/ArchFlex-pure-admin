package com.littlelee.base.dict.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author littlelee
 * @date 2020-06-28 07:42:28
 */
@Data
@Accessors(chain = true)
public class SysDict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

                            /**
     * 字典名称
     */
    @TableField(value = "DICT_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String dictName;
                        /**
     * 字典编码
     */
    @TableField(value = "DICT_CODE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String dictCode;
                        /**
     * 描述
     */
    @TableField(value = "DESCRIPTION", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String description;
                        /**
     * 删除状态
     */
    @TableField(value = "DEL_FLAG", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private Integer delFlag;
                        /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private Date createTime;
                        /**
     * 字典类型
     */
    @TableField(value = "TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED) 
    private String type;
            
}