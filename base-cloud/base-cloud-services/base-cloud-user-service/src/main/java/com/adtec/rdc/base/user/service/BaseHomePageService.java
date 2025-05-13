package com.adtec.rdc.base.user.service;

import java.util.List;

import com.adtec.rdc.base.user.model.bo.BaseHomePage;

public interface BaseHomePageService {
	BaseHomePage getBaseHomePage(String appId, String userId, String userName, List<String> roleIds);
}
