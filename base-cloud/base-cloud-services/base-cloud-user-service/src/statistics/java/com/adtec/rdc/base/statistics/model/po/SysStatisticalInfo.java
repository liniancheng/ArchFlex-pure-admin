package com.adtec.rdc.base.statistics.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 统计信息表
 * </p>
 *
 * @author hewei
 * @date 2020-01-17 16:47:20
 */
@Data
@Accessors(chain = true)
public class SysStatisticalInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 统计id
     */
    @TableId(value = "statistical_id", type = IdType.UUID)
    private String statisticalId;

    /**
     * 统计策略id
     */
    @TableField(value = "strategy_id", strategy = FieldStrategy.IGNORED) 
    private String strategyId;
    /**
     * 请求地址
     */
    @TableField(value = "request_url", strategy = FieldStrategy.IGNORED) 
    private String requestUrl;
    /**
     * 请求用户ID
     */
    @TableField(value = "request_user_id", strategy = FieldStrategy.IGNORED) 
    private String requestUserId;
    /**
     * 请求主机IP
     */
    @TableField(value = "request_host", strategy = FieldStrategy.IGNORED) 
    private String requestHost;
    /**
     * 用户代理
     */
    @TableField(value = "user_agent", strategy = FieldStrategy.IGNORED) 
    private String userAgent;
    /**
     * 请求方式
     */
    @TableField(value = "request_method", strategy = FieldStrategy.IGNORED) 
    private String requestMethod;
    /**
     * 请求时间
     */
    @TableField(value = "request_time", strategy = FieldStrategy.IGNORED) 
    private String requestTime;
    /**
     * 响应状态码
     */
    @TableField(value = "response_status", strategy = FieldStrategy.IGNORED) 
    private String responseStatus;

    /**
     * 响应状态码
     */
    @TableField(value = "response_data_size", strategy = FieldStrategy.IGNORED)
    private int responseDataSize;

    @TableField(exist = false)
    private SysStatisticalStrategy strategy;

    @TableField(exist = false)
    private String requestUserName;
}