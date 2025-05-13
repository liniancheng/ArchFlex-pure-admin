package com.adtec.rdc.base.crossvalidation.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkPlanDetailBo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanDetailQuery;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
public interface CvChkPlanDetailService extends BaseService<CvChkPlanDetail> {

    Boolean execSql(CvChkPlanDetailBo cvChkPlanDetailBo);
}
