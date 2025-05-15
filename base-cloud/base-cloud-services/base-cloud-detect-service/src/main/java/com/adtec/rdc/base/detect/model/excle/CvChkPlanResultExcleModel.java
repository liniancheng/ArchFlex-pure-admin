package com.adtec.rdc.base.crossvalidation.model.excle;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 检核计划结果表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
@Data
public class CvChkPlanResultExcleModel implements Serializable {

    /**
     * 指标名称
     */
    private String indNm;
    /**
     * 法人
     */
    private String orgNm;
    /**
     * 币种值
     */
    private String currencyVal;
    /**
     * 1104频度(R1104)
     */
    private String t1104Frq;
    private String t1104PartVal;
    /**
     * EAST频度(EAST3)
     */
    private String eastPartVal;
    private String eastFrq;
    /**
     * 金融统计大集中频度(JRTJ)
     */
    private String bigFocusPartVal;
    private String bigFocusFrq;
    /**
     * 客户风险频度(KHFX)
     */
    private String custRskPartVal;
    private String custRskFrq;
    /**
     * 金融基础数据频度(DWDK)
     */
    private String finBaseDataPartVal;
    private String finBaseDataFrq;
}
