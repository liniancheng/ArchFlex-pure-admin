package com.adtec.rdc.base.layout.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.layout.service.SysRoleLayoutItemRelService;
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
 * @date 2020-08-25 15:05:27
 */
@RestController
@RequestMapping("/itemRel")
@Api(value = "角色-布局数据项关系controller", tags = {"角色-布局数据项关系操作接口"})
public class SysRoleLayoutItemRelController extends BaseHttpController{
	private static final String FUNC_NAME = "角色-布局数据项数据项关系功能";

    @Autowired
    private SysRoleLayoutItemRelService service;
    
    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "新增布局数据项权限")
    @ApiOperation(value = "新增布局数据项权限", notes = "新增布局数据项权限", httpMethod = "POST")
    @PostMapping
    public ApiResult<Boolean> saveLayoutRel(@RequestParam("itemId") String itemId,@RequestParam("roleIds") List<String> roleIds){
    	return new ApiResult<>(service.saveItemRel(itemId,roleIds));
    }
    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "新增布局数据项权限")
    @ApiOperation(value = "删除布局数据项权限", notes = "新增布局数据项权限", httpMethod = "DELETE")
    @DeleteMapping
    public ApiResult<Boolean> deleteLayoutRel(@RequestParam("itemId") String itemId,@RequestParam("roleIds") List<String> roleIds){
    	return new ApiResult<>(service.deleteItemRel(itemId,roleIds));
    }
    
    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE, moduleName = FUNC_NAME, actionName = "删除布局数据项权限")
    @ApiOperation(value = "删除布局数据项权限", notes = "删除布局数据项权限信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "rptId", value = "布局数据项id", required = true, dataType = "string")
    @DeleteMapping("/{itemId}")
    public ApiResult<Boolean> deleteLayoutRel(@PathVariable("itemId") String itemId){
    	return new ApiResult<>(service.deleteItemRel(itemId,UserUtil.getAppId(request)));
    }
    
    @ApiOperation(value = "布局数据项权限列表", notes = "布局数据项权限列表", httpMethod = "GET")
    @ApiImplicitParam(name = "rptId", value = "布局数据项id", required = false, dataType = "string")
    @GetMapping("/list/{itemId}")
    public ApiResult<List<TransferNode>> listRole(@PathVariable("itemId") String itemId){
    	return new ApiResult<>(service.listRole(itemId, UserUtil.getAppId(request)));
    }

}
