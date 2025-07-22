package com.littlelee.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.bo.BaseHomePage;
import com.littlelee.base.user.service.BaseHomePageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/base/home")
@Tag(name = "首页信息获取接口", description = "首页 controller")
public class HomePageController extends BaseHttpController {

	@Autowired
	private BaseHomePageService service;

	@Operation(summary = "查询首页信息", description = "查询首页信息")
	@GetMapping("/page")
	public ApiResult<BaseHomePage> getBaseHomePage() {
		return new ApiResult<>(service.getBaseHomePage(
				UserUtil.getAppId(request),
				UserUtil.getUserId(request),
				UserUtil.getLoginName(request),
				UserUtil.getRoleIds(request)));
	}
}