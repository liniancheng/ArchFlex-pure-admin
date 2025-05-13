package com.adtec.rdc.base.statistics.model.query;

import java.util.Date;
import java.util.List;

import com.adtec.rdc.base.statistics.model.po.SysStatisticalInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author hewei
 * @date 2020-01-17 16:47:20
 */
@Data
public class SysStatisticalInfoQuery extends Page<SysStatisticalInfo> {

    /**
     * 统计id
     */
    private String statisticalId;
    /**
     * 统计策略id
     */
    private String strategyId;
    /**
     * 请求地址
     */
    private String requestUrl;
    /**
     * 请求用户ID
     */
    private String requestUserId;
    /**
     * 请求主机IP
     */
    private String requestHost;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 请求方式
     */
    private String requestMethod;
    /**
     * 请求时间
     */
    private String requestTime;
    /**
     * 响应状态码
     */
    private String responseStatus;

    private String requestTimeStart;

    private String requestTimeEnd;

}
