package com.adtec.rdc.base.crossvalidation.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 指标机构过滤表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
@Data
@Accessors(chain = true)
public class CvChkIndOrgRule implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

                            /**
     * 法人id
     */
    @TableField(value = "ORG_VAL", strategy = FieldStrategy.IGNORED)
    private String orgVal;
                        /**
     * 指标id
     */
    @TableField(value = "IND_NO", strategy = FieldStrategy.IGNORED)
    private String indNo;
                        /**
     * 是否可用(Y:是,N:否)
     */
    @TableField(value = "IS_USE", strategy = FieldStrategy.IGNORED)
    private String isUse;
                        /**
     * 是否可见(Y:是,N:否)
     */
    @TableField(value = "IS_SHOW", strategy = FieldStrategy.IGNORED)
    private String isShow;

    @TableField(exist = false)
    private String indNm;
    @TableField(exist = false)
    private String orgNm;
    @TableField(exist = false)
    private List<String> indNos;
    @TableField(exist = false)
    private List<String> orgVals;
}
