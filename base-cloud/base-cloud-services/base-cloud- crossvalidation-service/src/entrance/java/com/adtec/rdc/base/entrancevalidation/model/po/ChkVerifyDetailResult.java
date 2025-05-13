package com.adtec.rdc.base.entrancevalidation.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName ChkVerifyDetailResult
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 10:22:35
 **/
@Data
@Accessors(chain = true)
public class ChkVerifyDetailResult implements Serializable {

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
     * 列名
     */
    @TableField(value = "COL_NM", strategy = FieldStrategy.IGNORED)
    private String colNm;

    /**
     * 错误数据
     */
    @TableField(value = "ERR_DATA", strategy = FieldStrategy.IGNORED)
    private String errData;

}
