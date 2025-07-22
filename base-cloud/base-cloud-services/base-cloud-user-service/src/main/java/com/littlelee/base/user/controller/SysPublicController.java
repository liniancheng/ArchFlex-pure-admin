package com.littlelee.base.user.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.param.model.po.SysParamInfo;
import com.littlelee.base.param.service.SysParamInfoService;
import com.littlelee.base.user.model.bo.SystemCodeBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/public")
@Tag(name = "公共信息获取接口", description = "公共 controller")
public class SysPublicController extends BaseHttpController {

	@Autowired
	private SysParamInfoService paramInfoService;

	@Value("#{'${custom.public.code}'.split(',')}")
	private List<String> listCode;

	@Operation(summary = "系统配置信息", description = "系统配置信息")
	@GetMapping("/index")
	public ApiResult<SystemCodeBean> info() {
		String appId = UserUtil.getAppId(request);

		QueryWrapper<SysParamInfo> query = new QueryWrapper<>();
		query.lambda()
				.eq(SysParamInfo::getAppId, appId)
				.in(SysParamInfo::getParamName, listCode);
		List<SysParamInfo> list = paramInfoService.list(query);

		SystemCodeBean bean = new SystemCodeBean();
		if (list != null) {
			Map<String, String> map = list.stream()
					.collect(Collectors.toMap(
							SysParamInfo::getParamName,
							SysParamInfo::getParamValue,
							(k1, k2) -> k2));
			bean.setCodeMap(map);
		}
		return new ApiResult<>(bean);
	}
}