package com.adtec.rdc.base.crossvalidation.model.query;

import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndRuleChkVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@Data
public class CvRuleQuery extends Page<CvChkIndRuleChkVo> {
        /**
     * 指标编号
     */
    private List<String> indNos;
    /**
     * 系统规则值
     */
    private List<String> sysVals;
        /**
     * 机构法人规则
     */
    private String orgVal;

    /**
     * 指标类型
     */
    private String ruleVal;

    /**
     * 币种
     */
    private String currencyVal;
}
