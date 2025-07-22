package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceNodeAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-07-02 10:06:37
 */
@RestController
@RequestMapping("/SysWorkflowInstanceNodeAuth")
@Tag(name = "流程节点权限操作接口", description = "流程节点权限controller")
public class SysWorkflowInstanceNodeAuthController {

    private static final String FUNC_NAME = "流程节点权限功能";

    @Autowired
    private SysWorkflowInstanceNodeAuthService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加流程节点权限")
    @Operation(summary = "添加流程节点权限", description = "流程节点权限信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "流程节点权限信息", required = true)
            @RequestBody SysWorkflowInstanceNodeAuth sysWorkflowInstanceNodeAuth) {
        return new ApiResult<>(service.save(sysWorkflowInstanceNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改流程节点权限")
    @Operation(summary = "修改流程节点权限", description = "流程节点权限信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "流程节点权限信息", required = true)
            @RequestBody SysWorkflowInstanceNodeAuth sysWorkflowInstanceNodeAuth) {
        return new ApiResult<>(service.updateById(sysWorkflowInstanceNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除流程节点权限")
    @Operation(summary = "删除流程节点权限", description = "删除流程节点权限信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "流程节点权限id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询流程节点权限信息")
    @Operation(summary = "查询流程节点权限信息", description = "通过主键查询流程节点权限信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstanceNodeAuth> getById(
            @Parameter(description = "流程节点权限id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "流程节点权限信息分页查询", description = "流程节点权限信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysWorkflowInstanceNodeAuthQuery> pageByQuery(
            @Parameter(description = "流程节点权限信息查询类", required = false)
            SysWorkflowInstanceNodeAuthQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }
}