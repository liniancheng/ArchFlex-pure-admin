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
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.user.service.SysUserGroupRelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userGroupRel")
@Api(value = "用户组用户关系controller", tags = {"用户组用户关系接口"})
public class SysUserGroupRelController {

	private static final String FUNC_NAME = "用户组用户权限功能";

	@Autowired
	private SysUserGroupRelService service;
	@Autowired
	private HttpServletRequest request;
	
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "新增用户组权限")
	@ApiOperation(value = "新增用户组权限", notes = "新增用户组权限", httpMethod = "POST")
    @ApiImplicitParam(name = "userGroupRel", value = "新增用户组权限", required = true, dataType = "string")
	@PostMapping("/saveGroupRel")
	public ApiResult<Boolean> saveGroupRel(@RequestParam("groupId")String groupId, @RequestParam("userIds") List<String> userIds){
		try {
			return new ApiResult<>(service.saveGroupRel(groupId,userIds, UserUtil.getAppId(request)));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除用户组权限")
	@ApiOperation(value = "删除用户组权限", notes = "删除用户组权限", httpMethod = "DELETE")
	@ApiImplicitParam(name = "groupId", value = "用户组id", required = true, dataType = "string")
	@DeleteMapping("/deleteUserGroupRels")
	public ApiResult<Boolean> deleteRptRel(@RequestParam("groupId") String groupId, @RequestParam("userIds") List<String> userIds){
		try {
			return new ApiResult<>(service.deleteUserGroupRel(groupId,userIds));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);

		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "用户组用户权限")
	@ApiOperation(value = "用户组用户权限", notes = "用户组用户权限", httpMethod = "GET")
	@ApiImplicitParam(name = "groupId", value = "用户组id", required = false, dataType = "string")
	@GetMapping("/listUser/{id}")
	public ApiResult<List<TransferVo>> getUserList(@PathVariable("id")String id){
		try {
			return new ApiResult<>(service.getUserList(id));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
	
}
