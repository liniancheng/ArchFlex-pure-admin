package com.adtec.rdc.base.user.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.adtec.rdc.base.param.service.SysParamInfoService;
import com.adtec.rdc.base.user.model.bo.SystemCodeBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/public")
@Api(value = "公共controller", tags = { "公共信息获取接口" })
public class SysPublicController extends BaseHttpController {

	@Autowired
	private SysParamInfoService paramInfoService;
	@Value("#{'${custom.public.code}'.split(',')}")
	private List<String> listCode;

	@ApiOperation(value = "系统配置信息", notes = "系统配置信息", httpMethod = "GET")
	@GetMapping("/index")
	public ApiResult<SystemCodeBean> info() {
		String appId = UserUtil.getAppId(request);
		// 获取公共代码中相关配置
		QueryWrapper<SysParamInfo> query = new QueryWrapper<SysParamInfo>();
		query.lambda().eq(SysParamInfo::getAppId, appId).in(SysParamInfo::getParamName, listCode);
		List<SysParamInfo> list = paramInfoService.list(query);
		
		SystemCodeBean bean = new SystemCodeBean();
		if (list != null) {
			Map<String, String> map = list.stream()
					.collect(Collectors.toMap(SysParamInfo::getParamName, b -> b.getParamValue(), (k1, k2) -> k2));
			bean.setCodeMap(map);
		}
		return new ApiResult<SystemCodeBean>(bean);
	}

}
