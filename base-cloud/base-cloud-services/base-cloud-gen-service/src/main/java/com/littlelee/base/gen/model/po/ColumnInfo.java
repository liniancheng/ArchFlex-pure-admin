package com.littlelee.base.gen.model.po;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author: littlelee
 * @date: 2018/11/8 09:55
 */
@Data
public class ColumnInfo {

    /**
     * 列名
     */
    @TableId
    private String columnName;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 字段key类型
     */
    private String columnKey;

    /**
     * 是否可空
     */
    private String isNullable;

    private String extra;

}
