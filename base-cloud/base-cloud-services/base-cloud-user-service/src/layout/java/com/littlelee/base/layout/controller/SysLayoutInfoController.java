package com.littlelee.base.layout.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.layout.model.po.SysLayoutInfo;
import com.littlelee.base.layout.model.query.SysLayoutInfoQuery;
import com.littlelee.base.layout.service.SysLayoutInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-08-25 15:04:09
 */
@RestController
@RequestMapping("/layout")
@Tag(name = "首页布局操作接口", description = "首页布局 controller")
public class SysLayoutInfoController extends BaseHttpController {

    private static final String FUNC_NAME = "首页布局功能";

    @Autowired
    private SysLayoutInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加首页布局")
    @Operation(summary = "添加首页布局", description = "首页布局信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "首页布局信息", required = true)
            @RequestBody SysLayoutInfo layout) {
        layout.setAppId(UserUtil.getAppId(request));
        layout.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.save(layout));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改首页布局")
    @Operation(summary = "修改首页布局", description = "首页布局信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "首页布局信息", required = true)
            @RequestBody SysLayoutInfo layout) {
        layout.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(layout));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除首页布局")
    @Operation(summary = "删除首页布局", description = "删除首页布局信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "首页布局id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询首页布局信息")
    @Operation(summary = "查询首页布局信息", description = "通过主键查询首页布局信息")
    @GetMapping("/{id}")
    public ApiResult<SysLayoutInfo> getById(
            @Parameter(description = "首页布局id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "首页布局信息分页查询", description = "首页布局信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysLayoutInfoQuery> pageByQuery(
            @Parameter(description = "首页布局信息查询类", required = false)
            SysLayoutInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "布局预览", description = "布局预览")
    @GetMapping("/fetchOnce/{id}")
    public ApiResult<SysLayoutInfo> fetchOnce(
            @Parameter(description = "布局id", required = false)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.fetchOnce(id, UserUtil.getAppId(request)));
    }

    @Operation(summary = "获取用户自定义布局", description = "获取用户自定义布局")
    @GetMapping("/fetchByLoginName")
    public ApiResult<SysLayoutInfo> fetchByLoginName() {
        return new ApiResult<>(service.fetchByLoginName(UserUtil.getLoginName(request), UserUtil.getAppId(request)));
    }

    @Operation(summary = "加载布局", description = "加载布局")
    @GetMapping("/fetchLayout")
    public ApiResult<SysLayoutInfo> fetchLayout() {
        return new ApiResult<>(service.fetchLayout(
                UserUtil.getLoginName(request),
                UserUtil.getAppId(request),
                UserUtil.getRoleIds(request)));
    }
}