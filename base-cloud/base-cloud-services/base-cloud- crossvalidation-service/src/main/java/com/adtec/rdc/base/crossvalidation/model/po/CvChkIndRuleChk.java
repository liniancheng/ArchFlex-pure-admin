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
 * 指标规则检核表
 * </p>
 *
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@Data
@Accessors(chain = true)
public class CvChkIndRuleChk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ID", type = IdType.UUID)
    private String id;
    /**
     * 指标编号
     */
    @TableField(value = "IND_NO", strategy = FieldStrategy.IGNORED)
    private String indNo;
    /**
     * 系统
     */
    @TableField(value = "SYS_VAL", strategy = FieldStrategy.IGNORED)
    private String sysVal;
    /**
     * 法人
     */
    @TableField(value = "ORG_VAL", strategy = FieldStrategy.IGNORED)
    private String orgVal;
    /**
     * 检验sql
     */
    @TableField(value = "CHK_SQL", strategy = FieldStrategy.IGNORED)
    private String chkSql;
    /**
     * EAST频度
     */
    @TableField(value = "EAST_FRQ", strategy = FieldStrategy.IGNORED)
    private String eastFrq;
    /**
     * 金融基础数据频度
     */
    @TableField(value = "FIN_BASE_DATA_FRQ", strategy = FieldStrategy.IGNORED)
    private String finBaseDataFrq;
    /**
     * 客户风险频度
     */
    @TableField(value = "CUST_RSK_FRQ", strategy = FieldStrategy.IGNORED)
    private String custRskFrq;
    /**
     * 大集中频度
     */
    @TableField(value = "BIG_FOCUS_FRQ", strategy = FieldStrategy.IGNORED)
    private String bigFocusFrq;
    /**
     * 1104频度
     */
    @TableField(value = "T1104_FRQ", strategy = FieldStrategy.IGNORED)
    private String t1104Frq;
    /**
     * 客户组织号
     */
    @TableField(value = "CUST_ORG", strategy = FieldStrategy.IGNORED)
    private String custOrg;
    /**
     * 状态
     */
    @TableField(value = "STATE", strategy = FieldStrategy.IGNORED)
    private String state;
    /**
     * 指标类型
     */
    @TableField(value = "RULE_VAL", strategy = FieldStrategy.IGNORED)
    private String ruleVal = "01";
    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED)
    private String createTime;
    /**
     * 修改时间
     */
    @TableField(value = "UPDATE_TIME", strategy = FieldStrategy.IGNORED)
    private String updateTime;
    /**
     * 创建人
     */
    @TableField(value = "CREATE_USER", strategy = FieldStrategy.IGNORED)
    private String createUser;
    /**
     * 修改人
     */
    @TableField(value = "UPDATE_USER", strategy = FieldStrategy.IGNORED)
    private String updateUser;

    /**
     * 指标名
     */
    @TableField(exist = false)
    private String indNm;
    /**
     * 系统名
     */
    @TableField(exist = false)
    private String sysNm;
    /**
     * 法人名称
     */
    @TableField(exist = false)
    private String orgNm;

    /**
     * 频度
     */
    @TableField(exist = false)
    private String dateVal;
}
