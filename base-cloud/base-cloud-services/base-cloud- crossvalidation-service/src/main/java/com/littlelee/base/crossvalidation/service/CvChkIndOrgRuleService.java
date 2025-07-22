package com.littlelee.base.crossvalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.crossvalidation.model.po.CvChkIndOrgRule;
import com.littlelee.base.crossvalidation.model.query.CvChkIndOrgRuleQuery;

/**
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
public interface CvChkIndOrgRuleService extends BaseService<CvChkIndOrgRule> {
	CvChkIndOrgRuleQuery pageByQuery(CvChkIndOrgRuleQuery query);

	Boolean saveCvChkIndOrgRule(CvChkIndOrgRule cvindorg);

    Boolean delete(String id);
}
