package com.adtec.rdc.base.user.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.model.po.SysRoleMenuRel;
import com.adtec.rdc.base.user.service.SysRoleMenuRelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/roleMenuRel")
@Api(value = "角色菜单关系controller", tags = {"角色菜单关系接口"})
public class SysRoleMenuRelController {

	private static final String FUNC_NAME = "角色菜单权限功能";

	@Autowired
	private SysRoleMenuRelService service;
	@Autowired
	private HttpServletRequest request;
	
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "新增角色权限")
	@ApiOperation(value = "新增角色权限", notes = "新增角色权限", httpMethod = "POST")
    @ApiImplicitParam(name = "userMenuRel", value = "新增角色权限", required = true, dataType = "string")
	@PostMapping("/saveRoleMenuRel")
	public ApiResult<Boolean> saveMenuRel(@RequestParam("roleId")String roleId, @RequestParam("menuIds") List<String> menuIds, @RequestParam("appId")String appId){
		try {
			return new ApiResult<>(service.saveRoleMenuRel(roleId,menuIds, appId));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除角色权限")
	@ApiOperation(value = "删除角色权限", notes = "删除角色权限", httpMethod = "DELETE")
	@ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "string")
	@DeleteMapping("/deleteRoleMenuRels")
	public ApiResult<Boolean> deleteRptRel(@RequestParam("roleId") String roleId, @RequestParam("menuIds") List<String> menuIds){
		try {
			return new ApiResult<>(service.deleteRoleMenuRel(roleId,menuIds));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);

		}
	}

	@GetMapping("/treeNode/{id}")
    @ApiOperation(value = "获取所有菜单的树", notes = "获取所有菜单的树", httpMethod = "GET")
    public ApiResult<List<SysRoleMenuRel>> getAllMenuTreeNode(@PathVariable("id")String id){
        return new ApiResult<>(service.getAllMenuTreeNode(id));
    }
	
}
