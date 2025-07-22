package com.adtec.rdc.base.crossvalidation.model.bo;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 指标规则检核表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@Data
public class CvChkIndRuleChkVo implements Serializable {
    /**
     * id
     */
    private String id;
    /**
     * 指标编号
     */
    private String indNo;
    /**
     * 指标名
     */
    private String indNm;
    /**
     * 系统名
     */
    private String sysNm;
    /**
     * 系统编号
     */
    private String sysVal;
    /**
     * 法人
     */
    private String orgVal;
    /**
     * 法人名称
     */
    private String orgNm;
    /**
     * 币种值
     */
    private String currencyVal;
    /**
     * 频度
     */
    private String dateVal;
    /**
     * 检验sql
     */
    private String chkSql;
}
