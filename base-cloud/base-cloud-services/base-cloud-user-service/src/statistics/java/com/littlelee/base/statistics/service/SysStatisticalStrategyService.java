package com.littlelee.base.statistics.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.statistics.model.po.SysStatisticalStrategy;
import com.littlelee.base.statistics.model.query.SysStatisticalStrategyQuery;

/**
 * @author littlelee
 * @date 2020-01-15 17:47:49
 */
public interface SysStatisticalStrategyService extends BaseService<SysStatisticalStrategy> {
	SysStatisticalStrategyQuery pageByQuery(SysStatisticalStrategyQuery query);
}
