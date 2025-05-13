package com.adtec.rdc.base.tenant.controller;

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
import com.adtec.rdc.base.tenant.model.bo.TransferVo;
import com.adtec.rdc.base.tenant.service.SysUserAppRelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/userAppRel")
@Api(value = "租户用户关系controller", tags = {"租户用户关系接口"})
public class SysUserAppRelController {

	private static final String FUNC_NAME = "租户用户权限功能";

	@Autowired
	private SysUserAppRelService service;
	@Autowired
	private HttpServletRequest request;
	
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "新增租户权限")
	@ApiOperation(value = "新增租户权限", notes = "新增租户权限", httpMethod = "POST")
    @ApiImplicitParam(name = "userAppRel", value = "新增租户权限", required = true, dataType = "string")
	@PostMapping("/saveAppRel")
	public ApiResult<Boolean> saveAppRel(@RequestParam("appId")String appId, @RequestParam("userIds") List<String> userIds){
		try {
			return new ApiResult<>(service.saveAppRel(appId,userIds));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除租户权限")
	@ApiOperation(value = "删除租户权限", notes = "删除租户权限", httpMethod = "DELETE")
	@ApiImplicitParam(name = "appId", value = "租户id", required = true, dataType = "string")
	@DeleteMapping("/deleteUserAppRels")
	public ApiResult<Boolean> deleteRptRel(@RequestParam("appId") String appId, @RequestParam("userIds") List<String> userIds){
		try {
			return new ApiResult<>(service.deleteUserAppRel(appId,userIds));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);

		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "租户用户权限")
	@ApiOperation(value = "租户用户权限", notes = "租户用户权限", httpMethod = "GET")
	@ApiImplicitParam(name = "appId", value = "租户id", required = false, dataType = "string")
	@GetMapping("/listUser/{id}")
	public ApiResult<List<TransferVo>> getUserList(@PathVariable("id")String id){
		try {
			return new ApiResult<>(service.getUserList(id));
		}catch(ServiceException e) {
			return new ApiResult<>().failed(e);
		}
	}
	
}
