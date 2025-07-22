package com.littlelee.base.layout.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.layout.service.SysRoleLayoutRelService;
import com.littlelee.web.antd.bo.TransferNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-08-25 15:06:33
 */
@RestController
@RequestMapping("/layoutRel")
@Tag(name = "角色-布局关系操作接口", description = "角色-布局关系 controller")
public class SysRoleLayoutRelController extends BaseHttpController {

    private static final String FUNC_NAME = "角色-布局关系功能";

    @Autowired
    private SysRoleLayoutRelService service;

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "新增布局权限")
    @Operation(summary = "新增布局权限", description = "新增布局权限")
    @PostMapping
    public ApiResult<Boolean> saveLayoutRel(
            @Parameter(description = "布局ID", required = true)
            @RequestParam("layId") String layId,
            @Parameter(description = "角色ID列表", required = true)
            @RequestParam("roleIds") List<String> roleIds) {
        return new ApiResult<>(service.saveLayoutRel(layId, roleIds));
    }

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除布局权限")
    @Operation(summary = "删除布局权限", description = "删除布局权限")
    @DeleteMapping
    public ApiResult<Boolean> deleteLayoutRel(
            @Parameter(description = "布局ID", required = true)
            @RequestParam("layId") String layId,
            @Parameter(description = "角色ID列表", required = true)
            @RequestParam("roleIds") List<String> roleIds) {
        return new ApiResult<>(service.deleteLayoutRel(layId, roleIds));
    }

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除布局权限")
    @Operation(summary = "根据布局ID删除权限", description = "根据布局ID删除权限")
    @DeleteMapping("/{layId}")
    public ApiResult<Boolean> deleteLayoutRel(
            @Parameter(description = "布局ID", required = true)
            @PathVariable("layId") String layId) {
        return new ApiResult<>(service.deleteLayoutRel(layId, UserUtil.getAppId(request)));
    }

    @Operation(summary = "布局权限列表", description = "布局权限列表")
    @GetMapping("/list/{layId}")
    public ApiResult<List<TransferNode>> listRole(
            @Parameter(description = "布局ID", required = false)
            @PathVariable("layId") String layId) {
        return new ApiResult<>(service.listRole(layId, UserUtil.getAppId(request)));
    }
}