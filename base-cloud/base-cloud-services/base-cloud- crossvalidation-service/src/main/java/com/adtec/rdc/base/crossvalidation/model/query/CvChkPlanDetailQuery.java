package com.adtec.rdc.base.crossvalidation.model.query;

import java.util.Date;

import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2022-03-07 20:02:56
 */
@Data
public class CvChkPlanDetailQuery extends Page<CvChkPlanDetail> {

        /**
     * id
     */
    private String id;
        /**
     * 计划id
     */
    private String planId;
        /**
     * 指标编号
     */
    private String indNo;
        /**
     * 法人号
     */
    private String orgVal;
        /**
     * 频度
     */
    private String dateVal;
        /**
     * 系统编号
     */
    private String sysVal;
        /**
     * 执行sql
     */
    private String execSql;
        /**
     * 结果状态
     */
    private String resultStatus;
        /**
     * 数据时间
     */
    private String dataTime;
    
}
