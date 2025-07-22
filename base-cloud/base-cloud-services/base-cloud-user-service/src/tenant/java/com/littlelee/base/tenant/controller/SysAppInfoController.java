package com.littlelee.base.tenant.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.app.constants.AppConstants;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.tenant.model.bo.SysAppTree;
import com.littlelee.base.tenant.model.po.SysAppInfo;
import com.littlelee.base.tenant.service.SysAppInfoService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author liushp
 * @date 2020-03-16 21:51:43
 */
@RestController
@RequestMapping("/tenant")
@Tag(name = "租户管理操作接口", description = "租户管理相关操作")
public class SysAppInfoController {
    private static final String FUNC_NAME = "租户管理功能";

    @Autowired
    private SysAppInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加租户管理")
    @Operation(summary = "添加租户管理", description = "新增租户管理信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "租户管理信息", required = true)
            @RequestBody SysAppInfo app) {
        return new ApiResult<>(service.saveApp(app, UserUtil.getUserId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改租户管理")
    @Operation(summary = "修改租户管理", description = "更新租户管理信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "租户管理信息", required = true)
            @RequestBody SysAppInfo app) {
        return new ApiResult<>(service.updateByAppId(app));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除租户管理")
    @Operation(summary = "删除租户管理", description = "根据主键删除租户管理")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "租户管理id", required = true)
            @PathVariable("id") String id) {
        if (AppConstants.DEF_MAIN_APP_ID.equals(id)) {
            return ApiResult.failed("超级管理系统不能删除!");
        }
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询租户管理信息")
    @Operation(summary = "查询租户管理信息", description = "根据主键查询单条租户管理信息")
    @GetMapping("/{id}")
    public ApiResult<SysAppInfo> getById(
            @Parameter(description = "租户管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "获取所有租户树", description = "获取所有租户树")
    @GetMapping("/tree")
    public ApiResult<List<SysAppTree>> getAllAppTree(
            @Parameter(description = "租户查询条件", required = false) SysAppInfo app) {
        return new ApiResult<>(service.getAllAppTree(app));
    }

    @Operation(summary = "获取关联租户个数", description = "获取指定租户的关联个数")
    @GetMapping("/appRel/{id}")
    public ApiResult<Long> getAppRelCount(
            @Parameter(description = "租户id", required = true) @PathVariable("id") String id) {
        return new ApiResult<>(service.getAppRelCount(id));
    }

    @Operation(summary = "获取租户选择数据", description = "根据当前用户获取其可选租户树")
    @GetMapping("/getAppAuthByUserId")
    public ApiResult<List<SysAppTree>> getAppAuthByUserId(
            @Parameter(hidden = true) /* 由框架注入 */ HttpServletRequest request) {
        String userId = UserUtil.getUserId(request);
        return new ApiResult<>(service.getAppAuthByUserId(userId));
    }
}
