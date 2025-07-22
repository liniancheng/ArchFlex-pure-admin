package com.littlelee.base.user.service;

import com.littlelee.base.user.model.query.SysMessageInfoQuery;

public interface PersonPageService {
	SysMessageInfoQuery pageByQuery(SysMessageInfoQuery query);
	Boolean readMessage(String type, String id, String userId);
	Boolean deleteMessage(String type, String id, String userId);
}
