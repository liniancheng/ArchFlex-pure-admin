package com.adtec.rdc.base.crossvalidation.mapper;

import com.adtec.rdc.base.crossvalidation.model.bo.ResultBo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import com.adtec.rdc.base.common.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
public interface CvChkPlanDetailMapper extends BaseMapper<CvChkPlanDetail> {

    List<ResultBo> selectWbAndZjj(@Param("dataTime") String dataTime, @Param("orgVal") String orgVal);
}
