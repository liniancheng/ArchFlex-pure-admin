package com.littlelee.base.layout.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.layout.service.SysRoleLayoutItemRelService;
import com.littlelee.web.antd.bo.TransferNode;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2020-08-25 15:05:27
 */
@RestController
@RequestMapping("/itemRel")
@Tag(name = "角色-布局数据项关系操作接口", description = "角色-布局数据项关系 controller")
public class SysRoleLayoutItemRelController extends BaseHttpController {

    private static final String FUNC_NAME = "角色-布局数据项数据项关系功能";

    @Autowired
    private SysRoleLayoutItemRelService service;

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "新增布局数据项权限")
    @Operation(summary = "新增布局数据项权限", description = "新增布局数据项权限")
    @PostMapping
    public ApiResult<Boolean> saveLayoutRel(
            @Parameter(description = "布局数据项ID", required = true)
            @RequestParam("itemId") String itemId,
            @Parameter(description = "角色ID列表", required = true)
            @RequestParam("roleIds") List<String> roleIds) {
        return new ApiResult<>(service.saveItemRel(itemId, roleIds));
    }

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除布局数据项权限")
    @Operation(summary = "删除布局数据项权限", description = "删除布局数据项权限")
    @DeleteMapping
    public ApiResult<Boolean> deleteLayoutRel(
            @Parameter(description = "布局数据项ID", required = true)
            @RequestParam("itemId") String itemId,
            @Parameter(description = "角色ID列表", required = true)
            @RequestParam("roleIds") List<String> roleIds) {
        return new ApiResult<>(service.deleteItemRel(itemId, roleIds));
    }

    @SysLog(serviceId = ServiceNameConstants.UDS_CLOUD_REPORT_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除布局数据项权限")
    @Operation(summary = "根据布局数据项ID删除权限", description = "根据布局数据项ID删除权限")
    @DeleteMapping("/{itemId}")
    public ApiResult<Boolean> deleteLayoutRel(
            @Parameter(description = "布局数据项ID", required = true)
            @PathVariable("itemId") String itemId) {
        return new ApiResult<>(service.deleteItemRel(itemId, UserUtil.getAppId(request)));
    }

    @Operation(summary = "布局数据项权限列表", description = "布局数据项权限列表")
    @GetMapping("/list/{itemId}")
    public ApiResult<List<TransferNode>> listRole(
            @Parameter(description = "布局数据项ID", required = false)
            @PathVariable("itemId") String itemId) {
        return new ApiResult<>(service.listRole(itemId, UserUtil.getAppId(request)));
    }
}