package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.workflow.model.bo.SysWorkflowCopy;
import com.littlelee.base.workflow.model.bo.SysWorkflowTree;
import com.littlelee.base.workflow.model.po.SysWorkflowInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowInfoQuery;
import com.littlelee.base.workflow.service.SysWorkflowInfoService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

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
 * @date 2020-06-30 09:15:26
 */
@RestController
@RequestMapping("/workflow")
@Tag(name = "工作流操作接口", description = "工作流相关操作")
public class SysWorkflowInfoController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流功能";

    @Autowired
    private SysWorkflowInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流")
    @Operation(summary = "添加工作流", description = "新增工作流信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流信息", required = true)
            @RequestBody SysWorkflowInfo sysWorkflowInfo) {
        sysWorkflowInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(sysWorkflowInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流")
    @Operation(summary = "修改工作流", description = "更新工作流信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流信息", required = true)
            @RequestBody SysWorkflowInfo sysWorkflowInfo) {
        sysWorkflowInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(sysWorkflowInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流")
    @Operation(summary = "删除工作流", description = "根据主键删除工作流")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流信息")
    @Operation(summary = "查询工作流信息", description = "根据主键查询单条工作流信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInfo> getById(
            @Parameter(description = "工作流id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "工作流管理树", description = "获取工作流树形结构")
    @GetMapping("/tree")
    public ApiResult<List<SysWorkflowTree>> tree(
            @Parameter(description = "查询条件") SysWorkflowInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.tree(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "复制工作流")
    @Operation(summary = "复制工作流", description = "复制指定工作流")
    @PostMapping("/copy")
    public ApiResult<Boolean> copy(
            @Parameter(description = "工作流复制信息", required = true)
            @RequestBody SysWorkflowCopy sysWorkflowCopy) {
        return new ApiResult<>(service.copy(sysWorkflowCopy));
    }
}
