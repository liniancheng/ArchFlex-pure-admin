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
 * 检核计划结果表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
@Data
@Accessors(chain = true)
public class CvChkPlanResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;

                            /**
     * 计划id
     */
    @TableField(value = "PLAN_ID", strategy = FieldStrategy.IGNORED)
    private String planId;
                        /**
     * 指标编号
     */
    @TableField(value = "IND_NO", strategy = FieldStrategy.IGNORED)
    private String indNo;
                        /**
     * 法人
     */
    @TableField(value = "ORG_VAL", strategy = FieldStrategy.IGNORED)
    private String orgVal;
    /**
     * 法人
     */
    @TableField(exist = false)
    private String orgNm;
                        /**
     * 频度
     */
    @TableField(value = "DATE_VAL", strategy = FieldStrategy.IGNORED)
    private String dateVal;
                        /**
     * 币种值
     */
    @TableField(value = "CURRENCY_VAL", strategy = FieldStrategy.IGNORED)
    private String currencyVal;
                        /**
     * 系统值
     */
    @TableField(value = "SYS_VAL", strategy = FieldStrategy.IGNORED)
    private String sysVal;
                        /**
     * 结果值
     */
    @TableField(value = "RESULT_VAL", strategy = FieldStrategy.IGNORED)
    private String resultVal;

    /**
     * 指标名称
     */
    @TableField(exist = false)
    private String indNm;
    /**
     * 校验sql
     */
    @TableField(exist = false)
    private String chkSql;
    /**
     * 数据时间
     */
    @TableField(exist = false)
    private String dataTime;
    /**
     * 1104频度(R1104)
     */
    @TableField(exist = false)
    private String t1104Frq;
    /**
     * EAST频度(EAST3)
     */
    @TableField(exist = false)
    private String eastFrq;
    /**
     * 金融基础数据频度(DWDK)
     */
    @TableField(exist = false)
    private String finBaseDataFrq;
    /**
     * 客户风险频度(KHFX)
     */
    @TableField(exist = false)
    private String custRskFrq;
    /**
     * 金融统计大集中频度(JRTJ)
     */
    @TableField(exist = false)
    private String bigFocusFrq;

    @TableField(exist = false)
    private String t1104PartVal;//1104
    @TableField(exist = false)
    private String eastPartVal;//EAST
    @TableField(exist = false)
    private String finBaseDataPartVal;//金融基础数据管理系统
    @TableField(exist = false)
    private String custRskPartVal;//客户风险
    @TableField(exist = false)
    private String  bigFocusPartVal;//金融统计大集中
}
