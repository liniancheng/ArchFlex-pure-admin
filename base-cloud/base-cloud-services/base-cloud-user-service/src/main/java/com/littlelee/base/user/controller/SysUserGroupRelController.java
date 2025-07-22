package com.littlelee.base.user.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.tenant.model.bo.TransferVo;
import com.littlelee.base.user.service.SysUserGroupRelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/userGroupRel")
@Tag(name = "用户组用户关系接口", description = "用户组用户关系 controller")
public class SysUserGroupRelController {

	private static final String FUNC_NAME = "用户组用户权限功能";

	@Autowired
	private SysUserGroupRelService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "新增用户组权限")
	@Operation(summary = "新增用户组权限", description = "新增用户组权限")
	@PostMapping("/saveGroupRel")
	public ApiResult<Boolean> saveGroupRel(
			@Parameter(description = "用户组ID", required = true) @RequestParam("groupId") String groupId,
			@Parameter(description = "用户ID列表", required = true) @RequestParam("userIds") List<String> userIds) {
		try {
			return new ApiResult<>(service.saveGroupRel(groupId, userIds, UserUtil.getAppId(request)));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "删除用户组权限")
	@Operation(summary = "删除用户组权限", description = "删除用户组权限")
	@DeleteMapping("/deleteUserGroupRels")
	public ApiResult<Boolean> deleteRptRel(
			@Parameter(description = "用户组ID", required = true) @RequestParam("groupId") String groupId,
			@Parameter(description = "用户ID列表", required = true) @RequestParam("userIds") List<String> userIds) {
		try {
			return new ApiResult<>(service.deleteUserGroupRel(groupId, userIds));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "用户组用户权限")
	@Operation(summary = "用户组用户权限", description = "获取指定用户组下的用户列表（穿梭框格式）")
	@GetMapping("/listUser/{id}")
	public ApiResult<List<TransferVo>> getUserList(
			@Parameter(description = "用户组ID", required = false) @PathVariable("id") String id) {
		try {
			return new ApiResult<>(service.getUserList(id));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
}