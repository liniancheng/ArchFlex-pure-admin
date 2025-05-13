package com.adtec.rdc.base.tenant.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.app.constants.AppConstants;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.tenant.model.bo.SysAppTree;
import com.adtec.rdc.base.tenant.model.po.SysAppInfo;
import com.adtec.rdc.base.tenant.service.SysAppInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@RestController
@RequestMapping("/tenant")
@Api(value = "租户管理controller", tags = {"租户管理操作接口"})
public class SysAppInfoController {
	private static final String FUNC_NAME = "租户管理功能";

    @Autowired
    private SysAppInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加租户管理")
    @ApiOperation(value = "添加租户管理", notes = "租户管理信息", httpMethod = "POST")
    @ApiImplicitParam(name = "tenant", value = "租户管理信息", required = true, dataType = "SysAppInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysAppInfo app){
        return new ApiResult<>(service.saveApp(app, UserUtil.getUserId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改租户管理")
    @ApiOperation(value = "修改租户管理", notes = "租户管理信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "tenant", value = "租户管理信息", required = true, dataType = "SysAppInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysAppInfo app){
        return new ApiResult<>(service.updateByAppId(app));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除租户管理")
    @ApiOperation(value = "删除租户管理", notes = "删除租户管理信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "租户管理id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
    	if(AppConstants.DEF_MAIN_APP_ID.equals(id)) {
    		return ApiResult.failed("超级管理系统不能删除!");
    	}
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询租户管理信息")
    @ApiOperation(value = "查询租户管理信息", notes = "通过主键查询租户管理信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "租户管理id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysAppInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }
    
    @ApiOperation(value = "获取所有租户树", notes = "获取所有租户树", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "租户管理信息租户查询", required = false, dataType = "SysAppInfo")
    @GetMapping("/tree")
    public ApiResult<List<SysAppTree>> getAllAppTree(SysAppInfo app){
        return new ApiResult<>(service.getAllAppTree(app));
    }
    
    
    @ApiOperation(value = "获取关联租户个数", notes = "获取关联租户个数", httpMethod = "GET")
    @ApiImplicitParam(name = "getAppRelCount", value = "获取关联租户个数", required = false, dataType = "String")
    @GetMapping("/appRel/{id}")
    public ApiResult<Integer> getAppRelCount(@PathVariable("id") String id){
        return new ApiResult<>(service.getAppRelCount(id));
    }
    
    @ApiOperation(value = "获取租户选择数据", notes = "获取租户选择数据", httpMethod = "GET")
    @ApiImplicitParam(name = "getAppsByUserId", value = "获取租户选择数据", required = false, dataType = "String")
    @GetMapping("/getAppAuthByUserId")
    public ApiResult<List<SysAppTree>> getAppAuthByUserId() {
    	String userId = UserUtil.getUserId(request);
    	return new ApiResult<>(service.getAppAuthByUserId(userId));
    }
    
}
