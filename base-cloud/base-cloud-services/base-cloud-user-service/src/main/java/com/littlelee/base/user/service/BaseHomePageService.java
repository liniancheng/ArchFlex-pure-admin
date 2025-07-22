package com.littlelee.base.user.service;

import java.util.List;

import com.littlelee.base.user.model.bo.BaseHomePage;

public interface BaseHomePageService {
	BaseHomePage getBaseHomePage(String appId, String userId, String userName, List<String> roleIds);
}
