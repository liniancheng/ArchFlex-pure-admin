package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.workflow.model.po.SysWorkflowTypeInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowTypeInfoQuery;
import com.littlelee.base.workflow.service.SysWorkflowTypeInfoService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
 * @author littlelee
 * @date 2020-06-30 09:13:45
 */
@RestController
@RequestMapping("/workflowType")
@Tag(name = "工作流分类操作接口", description = "工作流分类controller")
public class SysWorkflowTypeInfoController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流分类功能";

    @Autowired
    private SysWorkflowTypeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流分类")
    @Operation(summary = "添加工作流分类", description = "工作流分类信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流分类信息", required = true)
            @RequestBody SysWorkflowTypeInfo sysWorkflowTypeInfo){
        sysWorkflowTypeInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(sysWorkflowTypeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流分类")
    @Operation(summary = "修改工作流分类", description = "工作流分类信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流分类信息", required = true)
            @RequestBody SysWorkflowTypeInfo sysWorkflowTypeInfo){
        sysWorkflowTypeInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(sysWorkflowTypeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流分类")
    @Operation(summary = "删除工作流分类", description = "删除工作流分类信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流分类id", required = true)
            @PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流分类信息")
    @Operation(summary = "查询工作流分类信息", description = "通过主键查询工作流分类信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowTypeInfo> getById(
            @Parameter(description = "工作流分类id", required = true)
            @PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "工作流分类信息分页查询", description = "工作流分类信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysWorkflowTypeInfoQuery> pageByQuery(
            @Parameter(description = "工作流分类信息查询类", required = false)
            SysWorkflowTypeInfoQuery query){
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
