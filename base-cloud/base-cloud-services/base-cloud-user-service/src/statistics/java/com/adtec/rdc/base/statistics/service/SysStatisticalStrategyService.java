package com.adtec.rdc.base.statistics.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalStrategy;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalStrategyQuery;

/**
 * @author hewei
 * @date 2020-01-15 17:47:49
 */
public interface SysStatisticalStrategyService extends BaseService<SysStatisticalStrategy> {
	SysStatisticalStrategyQuery pageByQuery(SysStatisticalStrategyQuery query);
}
