package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;
import java.util.List;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlan;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 20:00:06
 */
@Data
public class CvChkPlanQuery extends Page<CvChkPlan> {
    /**
     * 法人
     */
    private String orgVal;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 系统值
     */
    private String sysVal;
    /**
     * 系统值
     */
    private List<String> sysVals;
    /**
     * 数据日期
     */
    private String dataTime;
    /**
     * 计划状态
     */
    private String planState;

}
