package com.littlelee.base.crossvalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.crossvalidation.model.po.CvChkPlanResult;
import com.littlelee.base.crossvalidation.model.query.CvChkPlanResultQuery;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
public interface CvChkPlanResultService extends BaseService<CvChkPlanResult> {

    CvChkPlanResultQuery showResult(CvChkPlanResultQuery query);

    void exportResult(CvChkPlanResultQuery query,String absolutePath, String tempOutputFileName);
}
