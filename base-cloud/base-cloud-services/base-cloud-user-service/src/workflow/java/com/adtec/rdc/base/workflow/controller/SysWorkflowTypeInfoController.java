package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowTypeInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowTypeInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author adtec
 * @date 2020-06-30 09:13:45
 */
@RestController
@RequestMapping("/workflowType")
@Api(value = "工作流分类controller", tags = {"工作流分类操作接口"})
public class SysWorkflowTypeInfoController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流分类功能";

    @Autowired
    private SysWorkflowTypeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流分类")
    @ApiOperation(value = "添加工作流分类", notes = "工作流分类信息", httpMethod = "POST")
    @ApiImplicitParam(name = "SysWorkflowTypeInfo", value = "工作流分类信息", required = true, dataType = "SysWorkflowTypeInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowTypeInfo SysWorkflowTypeInfo){
    	SysWorkflowTypeInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(SysWorkflowTypeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流分类")
    @ApiOperation(value = "修改工作流分类", notes = "工作流分类信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "SysWorkflowTypeInfo", value = "工作流分类信息", required = true, dataType = "SysWorkflowTypeInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowTypeInfo SysWorkflowTypeInfo){
    	SysWorkflowTypeInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(SysWorkflowTypeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流分类")
    @ApiOperation(value = "删除工作流分类", notes = "删除工作流分类信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流分类id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流分类信息")
    @ApiOperation(value = "查询工作流分类信息", notes = "通过主键查询工作流分类信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流分类id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowTypeInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "工作流分类信息分页查询", notes = "工作流分类信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流分类信息查询类", required = false, dataType = "SysWorkflowTypeInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysWorkflowTypeInfoQuery> pageByQuery(SysWorkflowTypeInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
    	return new ApiResult<>(service.pageByQuery(query));
    }	
}
