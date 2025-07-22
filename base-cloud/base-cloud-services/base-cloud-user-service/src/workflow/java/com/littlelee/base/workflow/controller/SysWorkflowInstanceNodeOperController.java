package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeOperQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceNodeOperService;

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
 * @date 2020-06-30 09:14:51
 */
@RestController
@RequestMapping("/workflowInstanceNodeOper")
@Tag(name = "工作流实例节点权限操作接口", description = "工作流实例节点权限controller")
public class SysWorkflowInstanceNodeOperController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流实例节点权限功能";

    @Autowired
    private SysWorkflowInstanceNodeOperService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流实例节点权限")
    @Operation(summary = "添加工作流实例节点权限", description = "工作流实例节点权限信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流实例节点权限信息", required = true)
            @RequestBody SysWorkflowInstanceNodeOper sysWorkflowInstanceNodeOper) {
        return new ApiResult<>(service.save(sysWorkflowInstanceNodeOper));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流实例节点权限")
    @Operation(summary = "修改工作流实例节点权限", description = "工作流实例节点权限信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流实例节点权限信息", required = true)
            @RequestBody SysWorkflowInstanceNodeOper sysWorkflowInstanceNodeOper) {
        return new ApiResult<>(service.updateById(sysWorkflowInstanceNodeOper));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流实例节点权限")
    @Operation(summary = "删除工作流实例节点权限", description = "删除工作流实例节点权限信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流实例节点权限id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流实例节点权限信息")
    @Operation(summary = "查询工作流实例节点权限信息", description = "通过主键查询工作流实例节点权限信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstanceNodeOper> getById(
            @Parameter(description = "工作流实例节点权限id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "工作流实例节点权限信息分页查询", description = "工作流实例节点权限信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysWorkflowInstanceNodeOperQuery> pageByQuery(
            @Parameter(description = "工作流实例节点权限信息查询类", required = false)
            SysWorkflowInstanceNodeOperQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }
}