package com.adtec.rdc.base.layout.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.layout.service.SysRoleLayoutRelService;
import com.adtec.rdc.web.antd.bo.TransferNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengchf
 * @date 2020-08-25 15:06:33
 */
@RestController
@RequestMapping("/layoutRel")
@Api(value = "角色-布局关系controller", tags = {"角色-布局关系操作接口"})
public class SysRoleLayoutRelController extends BaseHttpController{
	private static final String FUNC_NAME = "角色-布局关系功能";

    @Autowired
    private SysRoleLayoutRelService service;

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "新增布局权限")
    @ApiOperation(value = "新增布局权限", notes = "新增布局权限", httpMethod = "POST")
    @PostMapping
    public ApiResult<Boolean> saveLayoutRel(@RequestParam("layId") String layId,@RequestParam("roleIds") List<String> roleIds){
    	return new ApiResult<>(service.saveLayoutRel(layId,roleIds));
    }
    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "新增布局权限")
    @ApiOperation(value = "删除布局权限", notes = "新增布局权限", httpMethod = "DELETE")
    @DeleteMapping
    public ApiResult<Boolean> deleteLayoutRel(@RequestParam("layId") String layId,@RequestParam("roleIds") List<String> roleIds){
    	return new ApiResult<>(service.deleteLayoutRel(layId,roleIds));
    }
    
    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "删除布局权限")
    @ApiOperation(value = "删除布局权限", notes = "删除布局权限信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "rptId", value = "布局id", required = true, dataType = "string")
    @DeleteMapping("/{layId}")
    public ApiResult<Boolean> deleteLayoutRel(@PathVariable("layId") String layId){
    	return new ApiResult<>(service.deleteLayoutRel(layId,UserUtil.getAppId(request)));
    }
    
    @ApiOperation(value = "布局权限列表", notes = "布局权限列表", httpMethod = "GET")
    @ApiImplicitParam(name = "rptId", value = "布局id", required = false, dataType = "string")
    @GetMapping("/list/{layId}")
    public ApiResult<List<TransferNode>> listRole(@PathVariable("layId") String layId){
    	return new ApiResult<>(service.listRole(layId, UserUtil.getAppId(request)));
    }


}
