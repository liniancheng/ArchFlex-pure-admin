package com.littlelee.base.crossvalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.littlelee.base.crossvalidation.model.query.CvChkIndRuleChkQuery;
import com.littlelee.base.crossvalidation.model.query.CvRuleQuery;

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
