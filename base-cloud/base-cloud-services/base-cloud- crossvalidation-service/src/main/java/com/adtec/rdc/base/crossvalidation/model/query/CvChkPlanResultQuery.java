package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;
import java.util.List;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 20:05:21
 */
@Data
public class CvChkPlanResultQuery extends Page<CvChkPlanResult> {
    /**
     * 计划id
     */
    private String planId;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 法人
     */
    private String orgVal;
    /**
     * 数据日期
     */
    private String dataTime;
}
