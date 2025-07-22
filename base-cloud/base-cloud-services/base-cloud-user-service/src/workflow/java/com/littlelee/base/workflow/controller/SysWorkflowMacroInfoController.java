package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.workflow.model.bo.SysWorkflowMacroTree;
import com.littlelee.base.workflow.model.po.SysWorkflowMacroInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowMacroInfoQuery;
import com.littlelee.base.workflow.service.SysWorkflowMacroInfoService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

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
 * @date 2020-07-05 07:55:01
 */
@RestController
@RequestMapping("/workflowMacro")
@Tag(name = "工作流宏变量操作接口", description = "工作流宏变量controller")
public class SysWorkflowMacroInfoController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流宏变量功能";

    @Autowired
    private SysWorkflowMacroInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流宏变量")
    @Operation(summary = "添加工作流宏变量", description = "工作流宏变量信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流宏变量信息", required = true)
            @RequestBody SysWorkflowMacroInfo sysWorkflowMacroInfo) {
        return new ApiResult<>(service.save(sysWorkflowMacroInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流宏变量")
    @Operation(summary = "修改工作流宏变量", description = "工作流宏变量信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流宏变量信息", required = true)
            @RequestBody SysWorkflowMacroInfo sysWorkflowMacroInfo) {
        return new ApiResult<>(service.updateById(sysWorkflowMacroInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流宏变量")
    @Operation(summary = "删除工作流宏变量", description = "删除工作流宏变量信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流宏变量id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流宏变量信息")
    @Operation(summary = "查询工作流宏变量信息", description = "通过主键查询工作流宏变量信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowMacroInfo> getById(
            @Parameter(description = "工作流宏变量id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "工作流宏变量信息分页查询", description = "工作流宏变量信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysWorkflowMacroInfoQuery> pageByQuery(
            @Parameter(description = "工作流宏变量信息查询类", required = false)
            SysWorkflowMacroInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @GetMapping("/tree")
    @Operation(summary = "工作流宏变量管理树", description = "工作流宏变量管理树")
    public ApiResult<List<SysWorkflowMacroTree>> tree(
            @Parameter(description = "工作流宏变量管理树查询类", required = false)
            SysWorkflowMacroInfoQuery query) {
        return new ApiResult<>(service.tree(query));
    }
}
