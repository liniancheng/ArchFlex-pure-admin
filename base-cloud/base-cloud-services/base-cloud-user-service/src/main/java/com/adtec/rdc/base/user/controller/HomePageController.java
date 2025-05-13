package com.adtec.rdc.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.bo.BaseHomePage;
import com.adtec.rdc.base.user.service.BaseHomePageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/base/home")
@Api(value = "首页controller", tags = { "首页信息获取接口" })
public class HomePageController extends BaseHttpController {
	@Autowired
	private BaseHomePageService service;
	
	@ApiOperation(value = "查询首页信息", notes = "查询首页信息", httpMethod = "GET")
	@GetMapping("/page")
	public ApiResult<BaseHomePage> getBaseHomePage() {
		return new ApiResult<>(service.getBaseHomePage(
				UserUtil.getAppId(request), 
				UserUtil.getUserId(request), 
				UserUtil.getLoginName(request), 
				UserUtil.getRoleIds(request)));
	}
}
