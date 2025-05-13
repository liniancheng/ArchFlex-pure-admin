package com.adtec.rdc.base.user.service;

import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;

public interface PersonPageService {
	SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query);
	Boolean readMessage(String type, String id, String userId);
	Boolean deleteMessage(String type, String id, String userId);
}
