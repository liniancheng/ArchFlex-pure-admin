package com.adtec.rdc.base.crossvalidation.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 指标信息表
 * </p>
 *
 * @author adtec
 * @date 2022-03-03 10:48:27
 */
@Data
@Accessors(chain = true)
public class CvChkInd implements Serializable {

    /**
     * 指标编号
     */
    @TableId(value = "IND_NO", type = IdType.UUID)
    private String indNo;

    /**
     * 指标名
     */
    @TableField(value = "IND_NM", strategy = FieldStrategy.IGNORED)
    private String indNm;
    /**
     * 指标等级
     */
    @TableField(value = "IND_LEVEL", strategy = FieldStrategy.IGNORED)
    private String indLevel;
    /**
     * 父级指标编号
     */
    @TableField(value = "PARENT_NO", strategy = FieldStrategy.IGNORED)
    private String parentNo;
    /**
     * 触警级别
     */
    @TableField(value = "ALARM_LEVEL", strategy = FieldStrategy.IGNORED)
    private String alarmLevel;
    /**
     * 指标体系
     */
    @TableField(value = "IND_SYS", strategy = FieldStrategy.IGNORED)
    private String indSys;
    /**
     * 参考指标
     */
    @TableField(value = "BUS_DIREC", strategy = FieldStrategy.IGNORED)
    private String busDirec;
    /**
     * 指标释义
     */
    @TableField(value = "IND_EXPR", strategy = FieldStrategy.IGNORED)
    private String indExpr;
    /**
     * 归口部门
     */
    @TableField(value = "DEPT_NO", strategy = FieldStrategy.IGNORED)
    private String deptNo;
    /**
     * 币种值
     */
    @TableField(value = "CURRENCY_VAL", strategy = FieldStrategy.IGNORED)
    private String currencyVal;
    /**
     * 频度
     */
    @TableField(value = "DATE_VAL", strategy = FieldStrategy.IGNORED)
    private String dateVal;
    /**
     * 标志
     */
    @TableField(value = "REMARK", strategy = FieldStrategy.IGNORED)
    private String remark;
    /**
     * 状态
     */
    @TableField(value = "STATE", strategy = FieldStrategy.IGNORED)
    private String state;
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED)
    private String createTime;
    /**
     * 创建用户
     */
    @TableField(value = "CREATE_USER", strategy = FieldStrategy.IGNORED)
    private String createUser;
    /**
     * 更新时间
     */
    @TableField(value = "UPDATE_TIME", strategy = FieldStrategy.IGNORED)
    private String updateTime;
    /**
     * 更新用户
     */
    @TableField(value = "UPDATE_USER", strategy = FieldStrategy.IGNORED)
    private String updateUser;
    /**
     * 菜单排序
     */
    @TableField(value = "MENU_ORDER", strategy = FieldStrategy.IGNORED)
    private String menuOrder;

    @TableField(exist = false)
    private String parentLevel;

    @TableField(exist = false)
    private Boolean updateIndLevel;
}
