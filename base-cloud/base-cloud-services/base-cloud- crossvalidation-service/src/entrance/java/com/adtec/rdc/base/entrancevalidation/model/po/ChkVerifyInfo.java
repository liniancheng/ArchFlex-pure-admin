package com.adtec.rdc.base.entrancevalidation.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName ChkVerifyInfo
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 10:03:27
 **/
@Data
@Accessors(chain = true)
public class ChkVerifyInfo implements Serializable {

    /**
     * 规则编号
     */
    @TableId(value = "RULE_NO", type = IdType.UUID)
    private String ruleNo;

    /**
     * 法人
     */
    @TableField(value = "LGPR_INST_CD", strategy = FieldStrategy.IGNORED)
    private String lgprInstCd;

    /**
     * 数据时间
     */
    @TableField(value = "DATA_DT", strategy = FieldStrategy.IGNORED)
    private String dataDt;

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
     * 规则描述
     */
    @TableField(value = "RULE_DSC", strategy = FieldStrategy.IGNORED)
    private String ruleDsc;

    /**
     * 错误条数
     */
    @TableField(value = "ERR_NUM", strategy = FieldStrategy.IGNORED)
    private String errNum;

    /**
     * 记录数
     */
    @TableField(value = "RCRD_NUM", strategy = FieldStrategy.IGNORED)
    private String rcrdNum;

    /**
     * 校验状态
     */
    @TableField(value = "VERF_STS", strategy = FieldStrategy.IGNORED)
    private String verfSts;

    /**
     * 校验状态描述
     */
    @TableField(value = "VERF_DSC", strategy = FieldStrategy.IGNORED)
    private String verfDsc;

}
