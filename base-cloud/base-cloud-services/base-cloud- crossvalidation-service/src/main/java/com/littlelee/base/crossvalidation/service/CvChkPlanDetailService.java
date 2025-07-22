package com.littlelee.base.crossvalidation.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.crossvalidation.model.bo.CvChkPlanDetailBo;
import com.littlelee.base.crossvalidation.model.po.CvChkPlanDetail;
import com.littlelee.base.crossvalidation.model.query.CvChkPlanDetailQuery;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
public interface CvChkPlanDetailService extends BaseService<CvChkPlanDetail> {

    Boolean execSql(CvChkPlanDetailBo cvChkPlanDetailBo);
}
