package com.littlelee.base.statistics.service;

import com.littlelee.base.common.base.service.BaseService;
import com.littlelee.base.statistics.model.po.SysStatisticalInfo;
import com.littlelee.base.statistics.model.query.SysStatisticalInfoQuery;

/**
 * @author littlelee
 * @date 2020-01-17 16:47:20
 */
public interface SysStatisticalInfoService extends BaseService<SysStatisticalInfo> {
	SysStatisticalInfoQuery pageByQuery(SysStatisticalInfoQuery query);
	Boolean saveStatistical(SysStatisticalInfo sysStatisticalInfo);
}
