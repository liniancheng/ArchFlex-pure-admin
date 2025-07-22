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
import com.littlelee.base.user.model.po.SysRoleMenuRel;
import com.littlelee.base.user.service.SysRoleMenuRelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/roleMenuRel")
@Tag(name = "角色菜单关系接口", description = "角色菜单关系 controller")
public class SysRoleMenuRelController {

	private static final String FUNC_NAME = "角色菜单权限功能";

	@Autowired
	private SysRoleMenuRelService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "新增角色权限")
	@Operation(summary = "新增角色权限", description = "新增角色权限")
	@PostMapping("/saveRoleMenuRel")
	public ApiResult<Boolean> saveMenuRel(
			@Parameter(description = "角色ID", required = true) @RequestParam("roleId") String roleId,
			@Parameter(description = "菜单ID列表", required = true) @RequestParam("menuIds") List<String> menuIds,
			@Parameter(description = "应用ID", required = true) @RequestParam("appId") String appId) {
		try {
			return new ApiResult<>(service.saveRoleMenuRel(roleId, menuIds, appId));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "删除角色权限")
	@Operation(summary = "删除角色权限", description = "删除角色权限")
	@DeleteMapping("/deleteRoleMenuRels")
	public ApiResult<Boolean> deleteRptRel(
			@Parameter(description = "角色ID", required = true) @RequestParam("roleId") String roleId,
			@Parameter(description = "菜单ID列表", required = true) @RequestParam("menuIds") List<String> menuIds) {
		try {
			return new ApiResult<>(service.deleteRoleMenuRel(roleId, menuIds));
		} catch (ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}

	@Operation(summary = "获取角色已关联菜单树节点", description = "根据角色ID获取已关联的菜单树节点")
	@GetMapping("/treeNode/{id}")
	public ApiResult<List<SysRoleMenuRel>> getAllMenuTreeNode(
			@Parameter(description = "角色ID", required = true) @PathVariable("id") String id) {
		return new ApiResult<>(service.getAllMenuTreeNode(id));
	}
}