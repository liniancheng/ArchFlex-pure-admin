package com.littlelee.base.crossvalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.crossvalidation.model.po.CvChkPlan;
import com.littlelee.base.crossvalidation.model.query.CvChkPlanQuery;

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
