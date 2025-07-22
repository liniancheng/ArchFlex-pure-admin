package com.littlelee.base.crossvalidation.mapper;

import com.littlelee.base.crossvalidation.model.bo.ResultBo;
import com.littlelee.base.crossvalidation.model.po.CvChkPlanDetail;
import com.littlelee.base.common.base.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
public interface CvChkPlanDetailMapper extends BaseMapper<CvChkPlanDetail> {

    List<ResultBo> selectWbAndZjj(@Param("dataTime") String dataTime, @Param("orgVal") String orgVal);
}
