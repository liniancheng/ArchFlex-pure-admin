package com.adtec.rdc.base.statistics.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.model.bo.StatisticalStrategy;
import com.adtec.rdc.base.statistics.mapper.SysStatisticalStrategyMapper;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalStrategy;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalStrategyQuery;
import com.adtec.rdc.base.statistics.service.SysStatisticalStrategyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author hewei
 * @date 2020-01-15 17:47:49
 */
@Service
public class SysStatisticalStrategyServiceImpl extends BaseServiceImpl<SysStatisticalStrategyMapper, SysStatisticalStrategy> implements SysStatisticalStrategyService {
	@Autowired
    private SysStatisticalStrategyMapper mapper;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public SysStatisticalStrategyQuery pageByQuery(SysStatisticalStrategyQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public boolean save(SysStatisticalStrategy strategy) {
		if (strategy == null) {
			return false;
		}
		strategy.setDelFlag("0");
		boolean ok = super.save(strategy);
		if ("1".equals(strategy.getConfigStatus())) {
			StatisticalStrategy statisticalStrategy = new StatisticalStrategy();
			statisticalStrategy.setStrategyId(strategy.getStrategyId());
			statisticalStrategy.setServiceUrl(strategy.getServiceUrl());
			redisTemplate.opsForSet().add(CommonConstants.BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY, statisticalStrategy);
		}
		return ok;
	}

	@Override
	public boolean updateById(SysStatisticalStrategy strategy) {
		strategy.setDelFlag("0");
		SysStatisticalStrategy old = this.getById(strategy.getStrategyId());
		StatisticalStrategy statisticalStrategy = new StatisticalStrategy();
		statisticalStrategy.setStrategyId(old.getStrategyId());
		statisticalStrategy.setServiceUrl(old.getServiceUrl());

		boolean ok = super.updateById(strategy);
		if ("1".equals(strategy.getConfigStatus())) {
			redisTemplate.opsForSet().remove(CommonConstants.BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY, statisticalStrategy);
			statisticalStrategy.setServiceUrl(strategy.getServiceUrl());
			redisTemplate.opsForSet().add(CommonConstants.BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY, statisticalStrategy);
		}
		return ok;
	}

	@Override
	public boolean removeById(Serializable id) {
		if(id == null || "".equals(id)) {
			return false;
		}
		SysStatisticalStrategy old = this.getById(id);
		StatisticalStrategy statisticalStrategy = new StatisticalStrategy();
		statisticalStrategy.setStrategyId(old.getStrategyId());
		statisticalStrategy.setServiceUrl(old.getServiceUrl());
//		boolean ok = this.removeById(id);
		boolean ok = super.removeById(id);
		redisTemplate.opsForSet().remove(CommonConstants.BASE_REDIS_LIST_LEY_STATISTICAL_STRATEGY, statisticalStrategy);
		return ok;
	}
}
