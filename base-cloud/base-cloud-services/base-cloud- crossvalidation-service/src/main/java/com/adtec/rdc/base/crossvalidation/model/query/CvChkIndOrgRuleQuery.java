package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndOrgRule;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
@Data
public class CvChkIndOrgRuleQuery extends Page<CvChkIndOrgRule> {

        /**
     * id
     */
    private String id;
        /**
     * 法人id
     */
    private String orgVal;
        /**
     * 指标id
     */
    private String indNo;
        /**
     * 是否可用(Y:是,N:否)
     */
    private String isUse;
        /**
     * 是否可见(Y:是,N:否)
     */
    private String isShow;
    
}
