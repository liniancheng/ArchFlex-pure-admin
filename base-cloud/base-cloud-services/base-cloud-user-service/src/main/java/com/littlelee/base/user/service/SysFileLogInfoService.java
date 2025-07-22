package com.littlelee.base.user.service;

import com.littlelee.base.user.model.query.SysOperlogTextQuery;

public interface SysFileLogInfoService {
	SysOperlogTextQuery pageTextByQuery(SysOperlogTextQuery query);
}
