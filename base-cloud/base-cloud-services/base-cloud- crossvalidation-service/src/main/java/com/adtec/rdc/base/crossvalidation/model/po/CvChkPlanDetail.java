package com.adtec.rdc.base.crossvalidation.model.po;

import com.adtec.rdc.base.crossvalidation.model.bo.ResultBo;
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
 * 计划明细登记表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
@Data
@Accessors(chain = true)
public class CvChkPlanDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 执行sql结果
     */
    @TableField(exist = false)
    private ResultBo resultBo;

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
     * 法人号
     */
    @TableField(value = "ORG_VAL", strategy = FieldStrategy.IGNORED)
    private String orgVal;
                        /**
     * 频度
     */
    @TableField(value = "DATE_VAL", strategy = FieldStrategy.IGNORED)
    private String dateVal;
                        /**
     * 系统编号
     */
    @TableField(value = "SYS_VAL", strategy = FieldStrategy.IGNORED)
    private String sysVal;
                        /**
     * 执行sql
     */
    @TableField(value = "EXEC_SQL", strategy = FieldStrategy.IGNORED)
    private String execSql;
                        /**
     * 结果状态 00:未校验  01:已校验
     */
    @TableField(value = "RESULT_STATUS", strategy = FieldStrategy.IGNORED)
    private String resultStatus = "00";
                        /**
     * 数据时间
     */
    @TableField(value = "DATA_TIME", strategy = FieldStrategy.IGNORED)
    private String dataTime;
}
