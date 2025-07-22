package com.littlelee.base.user.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysUserCommentInfo;
import com.littlelee.base.user.model.query.SysUserCommentInfoQuery;
import com.littlelee.base.user.service.SysUserCommentInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author littlelee
 * @date 2020-07-28 15:38:50
 */
@RestController
@RequestMapping("/comment")
@Tag(name = "用户评论管理操作接口", description = "用户评论管理 controller")
public class SysUserCommentInfoController extends BaseHttpController {

    private static final String FUNC_NAME = "用户评论管理功能";

    @Autowired
    private SysUserCommentInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加用户评论")
    @Operation(summary = "添加用户评论", description = "用户评论管理信息")
    @PostMapping
    public ApiResult<SysUserCommentInfo> save(
            @Parameter(description = "用户评论管理信息", required = true)
            @RequestBody SysUserCommentInfo sysUserCommentInfo) {
        sysUserCommentInfo.setAppId(UserUtil.getAppId(request));
        sysUserCommentInfo.setCreateUser(UserUtil.getUserId(request));
        sysUserCommentInfo.setCreateTime(LocalDateTime.now());
        sysUserCommentInfo.setMyComment(true);
        return new ApiResult<>(service.saveComment(sysUserCommentInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改用户评论")
    @Operation(summary = "修改用户评论", description = "用户评论管理信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "用户评论管理信息", required = true)
            @RequestBody SysUserCommentInfo sysUserCommentInfo) {
        return new ApiResult<>(service.updateById(sysUserCommentInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除用户评论")
    @Operation(summary = "删除用户评论", description = "删除用户评论管理信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "用户评论管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "查询用户评论管理信息")
    @Operation(summary = "查询用户评论管理信息", description = "通过主键查询用户评论管理信息")
    @GetMapping("/{id}")
    public ApiResult<SysUserCommentInfo> getById(
            @Parameter(description = "用户评论管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "用户评论管理信息分页查询", description = "用户评论管理信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysUserCommentInfoQuery> pageByQuery(
            @Parameter(description = "用户评论管理信息查询类", required = false)
            SysUserCommentInfoQuery query) {
        query.setCurrentUserId(UserUtil.getUserId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}