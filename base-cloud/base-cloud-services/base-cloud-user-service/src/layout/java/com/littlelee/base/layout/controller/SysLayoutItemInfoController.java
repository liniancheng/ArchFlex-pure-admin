package com.littlelee.base.layout.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.layout.model.po.SysLayoutItemInfo;
import com.littlelee.base.layout.model.query.SysLayoutItemInfoQuery;
import com.littlelee.base.layout.service.SysLayoutItemInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-08-25 15:17:06
 */
@RestController
@RequestMapping("/layoutItem")
@Tag(name = "布局数据项操作接口", description = "布局数据项 controller")
public class SysLayoutItemInfoController extends BaseHttpController {

    private static final String FUNC_NAME = "布局数据项功能";

    @Autowired
    private SysLayoutItemInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加布局数据项")
    @Operation(summary = "添加布局数据项", description = "布局数据项信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "布局数据项信息", required = true)
            @RequestBody SysLayoutItemInfo layoutItem) {
        layoutItem.setAppId(UserUtil.getAppId(request));
        layoutItem.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.save(layoutItem));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改布局数据项")
    @Operation(summary = "修改布局数据项", description = "布局数据项信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "布局数据项信息", required = true)
            @RequestBody SysLayoutItemInfo layoutItem) {
        layoutItem.setAppId(UserUtil.getAppId(request));
        layoutItem.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.updateById(layoutItem));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除布局数据项")
    @Operation(summary = "删除布局数据项", description = "删除布局数据项信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "布局数据项id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询布局数据项信息")
    @Operation(summary = "查询布局数据项信息", description = "通过主键查询布局数据项信息")
    @GetMapping("/{id}")
    public ApiResult<SysLayoutItemInfo> getById(
            @Parameter(description = "布局数据项id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "布局数据项信息分页查询", description = "布局数据项信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysLayoutItemInfoQuery> pageByQuery(
            @Parameter(description = "布局数据项信息查询类", required = false)
            SysLayoutItemInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "用户具备权限的布局数据项信息分页查询",
            description = "用户具备权限的布局数据项信息分页查询")
    @GetMapping("/fetchPerson")
    public ApiResult<SysLayoutItemInfoQuery> fetchPerson(
            @Parameter(description = "用户具备权限的布局数据项信息查询类", required = false)
            SysLayoutItemInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.fetchPerson(query, UserUtil.getRoleIds(request)));
    }
}