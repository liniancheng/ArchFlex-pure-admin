package com.littlelee.base.crossvalidation.model.po;

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
 * 检核计划表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 20:00:06
 */
@Data
@Accessors(chain = true)
public class CvChkPlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    private String id;

                            /**
     * 计划名称
     */
    @TableField(value = "PLAN_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String planName;
                        /**
     * 计划状态 1.未完成 2.已完成
     */
    @TableField(value = "PLAN_STATE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String planState;
                        /**
     * 计划类型 1.实时执行 2.定时执行
     */
    @TableField(value = "PLAN_TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String planType;
                        /**
     * 执行时间
     */
    @TableField(value = "EXEC_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String execTime;
                        /**
     * 法人
     */
    @TableField(value = "ORG_VAL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String orgVal;
                        /**
     * 频度
     */
    @TableField(value = "DATE_VAL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String dateVal;
                        /**
     * 币种
     */
    @TableField(value = "CURRENCY_VAL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String currencyVal;
                        /**
     * 指标集合
     */
    @TableField(value = "IND_LIST", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String indList;
                        /**
     * 系统集合
     */
    @TableField(value = "SYS_LIST", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String sysList;
                        /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String createTime;
                        /**
     * 创建人
     */
    @TableField(value = "CREATE_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String createUser;
                        /**
     * 数据时间
     */
    @TableField(value = "DATA_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String dataTime;

    @TableField(exist = false)
    private List<String> indNos;
    @TableField(exist = false)
    private List<String> sysVals;
}
