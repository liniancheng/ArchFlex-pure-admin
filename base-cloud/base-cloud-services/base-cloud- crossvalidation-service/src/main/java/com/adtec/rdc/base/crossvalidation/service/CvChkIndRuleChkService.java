package com.adtec.rdc.base.crossvalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndRuleChkQuery;
import com.adtec.rdc.base.crossvalidation.model.query.CvRuleQuery;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
public interface CvChkIndRuleChkService extends BaseService<CvChkIndRuleChk> {
	CvChkIndRuleChkQuery pageByQuery(CvChkIndRuleChkQuery query);

    Boolean saveCvChkIndRuleChk(CvChkIndRuleChk cvindrule);

    Boolean updateCvChkIndRuleChk(CvChkIndRuleChk cvChkIndRuleChk);

    CvChkIndRuleChk getById(String id);

    CvRuleQuery rulePageByQuery(CvRuleQuery query);
}
