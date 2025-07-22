package com.littlelee.base.statistics.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.statistics.mapper.SysStatisticalInfoMapper;
import com.littlelee.base.statistics.model.po.SysStatisticalInfo;
import com.littlelee.base.statistics.model.query.SysStatisticalInfoQuery;
import com.littlelee.base.statistics.service.SysStatisticalInfoService;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author littlelee
 * @date 2020-01-17 16:47:20
 */
@Service
public class SysStatisticalInfoServiceImpl extends BaseServiceImpl<SysStatisticalInfoMapper, SysStatisticalInfo> implements SysStatisticalInfoService {
	@Autowired
    private SysStatisticalInfoMapper mapper;
	
	@Override
	public SysStatisticalInfoQuery pageByQuery(SysStatisticalInfoQuery query) {
		query.addOrder(OrderItem.desc("request_time"));
		mapper.pageByQuery(query);
        return query;
	}

	@Async
	@Override
	public Boolean saveStatistical(SysStatisticalInfo info) {
		return this.save(info);
	}
}
