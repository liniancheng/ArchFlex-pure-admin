package com.adtec.rdc.base.crossvalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanResultQuery;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
public interface CvChkPlanResultService extends BaseService<CvChkPlanResult> {

    CvChkPlanResultQuery showResult(CvChkPlanResultQuery query);

    void exportResult(CvChkPlanResultQuery query,String absolutePath, String tempOutputFileName);
}
