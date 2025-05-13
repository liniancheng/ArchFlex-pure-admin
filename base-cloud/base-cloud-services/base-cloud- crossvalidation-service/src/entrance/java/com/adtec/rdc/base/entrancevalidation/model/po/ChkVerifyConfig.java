package com.adtec.rdc.base.entrancevalidation.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName ChkVerifyConfig
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 09:33:57
 **/
@Data
@Accessors(chain = true)
public class ChkVerifyConfig implements Serializable {

    /**
     * 规则编号
     */
    @TableId(value = "RULE_NO", type = IdType.UUID)
    private String ruleNo;

    /**
     * 系统名
     */
    @TableField(value = "SYS_NM", strategy = FieldStrategy.IGNORED)
    private String sysNm;

    /**
     * 系统表名
     */
    @TableField(value = "SYS_TAB_NM", strategy = FieldStrategy.IGNORED)
    private String sysTabNm;

    /**
     * 系统表中文名
     */
    @TableField(value = "SYS_TAB_CHN_NM", strategy = FieldStrategy.IGNORED)
    private String sysTabChnNm;

    /**
     * 列名
     */
    @TableField(value = "COL_NM", strategy = FieldStrategy.IGNORED)
    private String colNm;

    /**
     * 列中文名
     */
    @TableField(value = "COL_CHN_NM", strategy = FieldStrategy.IGNORED)
    private String colChnNm;

    /**
     * 规则类型
     */
    @TableField(value = "RLTYP_DSC", strategy = FieldStrategy.IGNORED)
    private String rltypDsc;

    /**
     * 规则描述
     */
    @TableField(value = "RULE_DSC", strategy = FieldStrategy.IGNORED)
    private String ruleDsc;

    /**
     * 校验SQL信息
     */
    @TableField(value = "VERISQL_INF", strategy = FieldStrategy.IGNORED)
    private String verisqlInf;

    /**
     * 是否可用
     */
    @TableField(value = "VLD_FLG", strategy = FieldStrategy.IGNORED)
    private String vldFlg;

}
