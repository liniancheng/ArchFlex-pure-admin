package com.adtec.rdc.base.crossvalidation.model.po;

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
 * 数据字典表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 20:05:47
 */
@Data
@Accessors(chain = true)
public class CvDictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 字典类型
     */
    @TableId(value = "DICT_TYPE", type = IdType.UUID)
    private String dictType;

                            /**
     * 字典key
     */
    @TableField(value = "DICT_KEY", strategy = FieldStrategy.IGNORED) 
    private String dictKey;
                        /**
     * 字典值
     */
    @TableField(value = "DICT_VALUE", strategy = FieldStrategy.IGNORED) 
    private String dictValue;
                        /**
     * key描述
     */
    @TableField(value = "KEY_DESC", strategy = FieldStrategy.IGNORED) 
    private String keyDesc;
                        /**
     * 值描述
     */
    @TableField(value = "VALUE_EX", strategy = FieldStrategy.IGNORED) 
    private String valueEx;
                        /**
     * 系统key
     */
    @TableField(value = "SYSTEM_KEY", strategy = FieldStrategy.IGNORED) 
    private String systemKey;
                        /**
     * 
     */
    @TableField(value = "V1", strategy = FieldStrategy.IGNORED) 
    private String v1;
                        /**
     * 
     */
    @TableField(value = "V2", strategy = FieldStrategy.IGNORED) 
    private String v2;
                        /**
     * 
     */
    @TableField(value = "V3", strategy = FieldStrategy.IGNORED) 
    private String v3;
                        /**
     * 
     */
    @TableField(value = "SS_ID", strategy = FieldStrategy.IGNORED) 
    private String ssId;
            
}