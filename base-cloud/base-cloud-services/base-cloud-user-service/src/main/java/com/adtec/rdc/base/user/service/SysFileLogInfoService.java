package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.user.model.query.SysOperlogTextQuery;

public interface SysFileLogInfoService {
	SysOperlogTextQuery pageTextByQuery(SysOperlogTextQuery query);
}
