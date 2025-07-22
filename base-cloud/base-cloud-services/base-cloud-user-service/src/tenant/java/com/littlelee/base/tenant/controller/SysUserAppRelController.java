package com.littlelee.base.tenant.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.tenant.model.bo.TransferVo;
import com.littlelee.base.tenant.service.SysUserAppRelService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/userAppRel")
@Tag(name = "租户用户关系接口", description = "租户与用户权限相关操作")
public class SysUserAppRelController {

	private static final String FUNC_NAME = "租户用户权限功能";

	@Autowired
	private SysUserAppRelService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "新增租户权限")
	@Operation(summary = "新增租户权限", description = "为用户批量绑定租户权限")
	@PostMapping("/saveAppRel")
	public ApiResult<Boolean> saveAppRel(
			@Parameter(description = "租户id", required = true) @RequestParam("appId") String appId,
			@Parameter(description = "用户id列表", required = true) @RequestParam("userIds") List<String> userIds) {
		try {
			return new ApiResult<>(service.saveAppRel(appId, userIds));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除租户权限")
	@Operation(summary = "删除租户权限", description = "批量移除用户的租户权限")
	@DeleteMapping("/deleteUserAppRels")
	public ApiResult<Boolean> deleteRptRel(
			@Parameter(description = "租户id", required = true) @RequestParam("appId") String appId,
			@Parameter(description = "用户id列表", required = true) @RequestParam("userIds") List<String> userIds) {
		try {
			return new ApiResult<>(service.deleteUserAppRel(appId, userIds));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "租户用户权限")
	@Operation(summary = "租户用户权限", description = "根据租户id获取可选/已选用户穿梭列表")
	@GetMapping("/listUser/{id}")
	public ApiResult<List<TransferVo>> getUserList(
			@Parameter(description = "租户id", required = true) @PathVariable("id") String id) {
		try {
			return new ApiResult<>(service.getUserList(id));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
}
