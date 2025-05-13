package com.adtec.rdc.base.dict.model.po;

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
 * @author adtec
 * @date 2020-06-28 07:42:28
 */
@Data
@Accessors(chain = true)
public class SysDict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

                            /**
     * 字典名称
     */
    @TableField(value = "DICT_NAME", strategy = FieldStrategy.IGNORED) 
    private String dictName;
                        /**
     * 字典编码
     */
    @TableField(value = "DICT_CODE", strategy = FieldStrategy.IGNORED) 
    private String dictCode;
                        /**
     * 描述
     */
    @TableField(value = "DESCRIPTION", strategy = FieldStrategy.IGNORED) 
    private String description;
                        /**
     * 删除状态
     */
    @TableField(value = "DEL_FLAG", strategy = FieldStrategy.IGNORED) 
    private Integer delFlag;
                        /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED) 
    private Date createTime;
                        /**
     * 字典类型
     */
    @TableField(value = "TYPE", strategy = FieldStrategy.IGNORED) 
    private String type;
            
}