package com.adtec.rdc.base.statistics.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.statistics.mapper.SysStatisticalInfoMapper;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalInfo;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalInfoQuery;
import com.adtec.rdc.base.statistics.service.SysStatisticalInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author hewei
 * @date 2020-01-17 16:47:20
 */
@Service
public class SysStatisticalInfoServiceImpl extends BaseServiceImpl<SysStatisticalInfoMapper, SysStatisticalInfo> implements SysStatisticalInfoService {
	@Autowired
    private SysStatisticalInfoMapper mapper;
	
	@Override
	public SysStatisticalInfoQuery pageByQuery(SysStatisticalInfoQuery query) {
		query.setDesc("request_time");
		mapper.pageByQuery(query);
        return query;
	}

	@Async
	@Override
	public Boolean saveStatistical(SysStatisticalInfo info) {
		return this.save(info);
	}
}
