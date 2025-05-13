package com.adtec.rdc.base.statistics.model.query;

import java.util.Date;

import com.adtec.rdc.base.statistics.model.po.SysStatisticalStrategy;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author hewei
 * @date 2020-01-15 17:47:49
 */
@Data
public class SysStatisticalStrategyQuery extends Page<SysStatisticalStrategy> {

    /**
     * 策略id
     */
    private String strategyId;
    /**
     * 服务地址
     */
    private String serviceUrl;
    /**
     * 功能名称
     */
    private String functionName;
    /**
     * 是否重复计数,0-否;1-是
     */
    private String repeatFlag;
    /**
     * 策略状态,0-停用;1-启用
     */
    private String configStatus;
    /**
     * 删除标记
     */
    private String delFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifyTime;

}
