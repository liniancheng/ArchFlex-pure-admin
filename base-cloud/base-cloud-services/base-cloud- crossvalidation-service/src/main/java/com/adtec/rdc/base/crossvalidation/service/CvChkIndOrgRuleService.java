package com.adtec.rdc.base.crossvalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndOrgRule;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndOrgRuleQuery;

/**
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
public interface CvChkIndOrgRuleService extends BaseService<CvChkIndOrgRule> {
	CvChkIndOrgRuleQuery pageByQuery(CvChkIndOrgRuleQuery query);

	Boolean saveCvChkIndOrgRule(CvChkIndOrgRule cvindorg);

    Boolean delete(String id);
}
