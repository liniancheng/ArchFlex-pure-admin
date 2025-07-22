package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.workflow.model.po.SysUserTaskExt;
import com.littlelee.base.workflow.model.query.SysUserTaskExtQuery;
import com.littlelee.base.workflow.service.SysUserTaskExtService;

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
 * @date 2020-07-20 19:19:57
 */
@RestController
@RequestMapping("/userTaskExt")
@Tag(name = "自定义任务操作接口", description = "自定义任务相关操作")
public class SysUserTaskExtController extends BaseHttpController {
    private static final String FUNC_NAME = "自定义任务功能";

    @Autowired
    private SysUserTaskExtService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加自定义任务")
    @Operation(summary = "添加自定义任务", description = "新增自定义任务信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "自定义任务信息", required = true)
            @RequestBody SysUserTaskExt sysUserTaskExt) {
        sysUserTaskExt.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(sysUserTaskExt));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改自定义任务")
    @Operation(summary = "修改自定义任务", description = "更新自定义任务信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "自定义任务信息", required = true)
            @RequestBody SysUserTaskExt sysUserTaskExt) {
        sysUserTaskExt.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(sysUserTaskExt));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除自定义任务")
    @Operation(summary = "删除自定义任务", description = "根据主键删除自定义任务")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "自定义任务id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询自定义任务信息")
    @Operation(summary = "查询自定义任务信息", description = "根据主键查询单条自定义任务信息")
    @GetMapping("/{id}")
    public ApiResult<SysUserTaskExt> getById(
            @Parameter(description = "自定义任务id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "自定义任务信息分页查询", description = "分页条件查询自定义任务列表")
    @GetMapping("/page")
    public ApiResult<SysUserTaskExtQuery> pageByQuery(
            @Parameter(description = "查询条件") SysUserTaskExtQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
