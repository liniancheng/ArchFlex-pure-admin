package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;
import java.util.List;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@Data
public class CvChkIndRuleChkQuery extends Page<CvChkIndRuleChk> {
        /**
     * 指标编号
     */
    private String indNo;
        /**
     * 系统
     */
    private String sysVal;
        /**
     * 法人
     */
    private String orgVal;

    /**
     * 指标类型
     */
    private String ruleVal;
}
