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
 * 字典明细表
 * </p>
 *
 * @author adtec
 * @date 2020-06-28 07:49:50
 */
@Data
@Accessors(chain = true)
public class SysDictItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

                            /**
     * 字典ID
     */
    @TableField(value = "DICT_ID", strategy = FieldStrategy.IGNORED) 
    private String dictId;
                        /**
     * 字典项文本
     */
    @TableField(value = "ITEM_TEXT", strategy = FieldStrategy.IGNORED) 
    private String itemText;
                        /**
     * 字典项值
     */
    @TableField(value = "ITEM_VALUE", strategy = FieldStrategy.IGNORED) 
    private String itemValue;
                        /**
     * 描述
     */
    @TableField(value = "DESCRIPTION", strategy = FieldStrategy.IGNORED) 
    private String description;
                        /**
     * 排序
     */
    @TableField(value = "SORT_ORDER", strategy = FieldStrategy.IGNORED) 
    private Integer sortOrder;
                        /**
     * 状态（1启用 0不启用）
     */
    @TableField(value = "STATUS", strategy = FieldStrategy.IGNORED) 
    private Integer status;
                        /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED) 
    private Date createTime;
            
}