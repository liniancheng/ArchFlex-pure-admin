package com.adtec.rdc.base.crossvalidation.mapper;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanResultQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
public interface CvChkPlanResultMapper extends BaseMapper<CvChkPlanResult> {
    IPage<CvChkPlanResult> showResult(CvChkPlanResultQuery query);

    List<String> getIndNo(@Param("planId")String planId, @Param("orgVal")String orgVal);
}
