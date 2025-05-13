package com.adtec.rdc.base.statistics.service;

import com.adtec.rdc.base.common.base.service.BaseService;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalInfo;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalInfoQuery;

/**
 * @author hewei
 * @date 2020-01-17 16:47:20
 */
public interface SysStatisticalInfoService extends BaseService<SysStatisticalInfo> {
	SysStatisticalInfoQuery pageByQuery(SysStatisticalInfoQuery query);
	Boolean saveStatistical(SysStatisticalInfo sysStatisticalInfo);
}
