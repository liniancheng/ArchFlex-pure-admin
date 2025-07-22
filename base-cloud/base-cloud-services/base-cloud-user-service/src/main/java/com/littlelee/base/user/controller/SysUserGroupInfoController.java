package com.littlelee.base.user.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysUserGroupInfo;
import com.littlelee.base.user.model.query.SysUserGroupInfoQuery;
import com.littlelee.base.user.service.SysUserGroupInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
@RestController
@RequestMapping("/usergroup")
@Tag(name = "用户组操作接口", description = "用户组 controller")
public class SysUserGroupInfoController {

    private static final String FUNC_NAME = "用户组功能";

    @Autowired
    private SysUserGroupInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加用户组")
    @Operation(summary = "添加用户组", description = "添加用户组")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "用户组信息", required = true)
            @RequestBody SysUserGroupInfo group) {
        group.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.saveGroup(group));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改用户组")
    @Operation(summary = "修改用户组", description = "修改用户组")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "用户组信息", required = true)
            @RequestBody SysUserGroupInfo group) {
        group.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateGroupById(group));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除用户组")
    @Operation(summary = "删除用户组", description = "删除用户组信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "用户组id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "查询用户组信息")
    @Operation(summary = "查询用户组信息", description = "通过主键查询用户组信息")
    @GetMapping("/{id}")
    public ApiResult<SysUserGroupInfoQuery> getById(
            @Parameter(description = "用户组id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.findGroupListById(id));
    }

    @Operation(summary = "用户组信息分页查询", description = "用户组信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysUserGroupInfoQuery> pageByQuery(
            @Parameter(description = "用户组信息查询类", required = false)
            SysUserGroupInfoQuery query) {
        // query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}