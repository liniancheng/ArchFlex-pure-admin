package com.adtec.rdc.base.crossvalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlan;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanQuery;

import java.util.Map;

/**
 * @author adtec
 * @date 2022-03-07 20:00:06
 */
public interface CvChkPlanService extends BaseService<CvChkPlan> {
	CvChkPlanQuery pageByQuery(CvChkPlanQuery query);

    Map<String,String> saveCvChkPlan(CvChkPlan cvchkplan);

    Boolean deletePlan(String id);
}
