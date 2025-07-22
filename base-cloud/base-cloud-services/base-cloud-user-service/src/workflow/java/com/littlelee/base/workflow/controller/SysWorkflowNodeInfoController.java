package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.workflow.model.bo.SysWorkflowNodeTree;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeInfo;
import com.littlelee.base.workflow.model.query.SysWorkflowInfoQuery;
import com.littlelee.base.workflow.service.SysWorkflowNodeInfoService;

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
 * @date 2020-06-30 09:14:21
 */
@RestController
@RequestMapping("/workflowNode")
@Tag(name = "工作流节点操作接口", description = "工作流节点controller")
public class SysWorkflowNodeInfoController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流节点功能";

    @Autowired
    private SysWorkflowNodeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流节点")
    @Operation(summary = "添加工作流节点", description = "工作流节点信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流节点信息", required = true)
            @RequestBody SysWorkflowNodeInfo sysWorkflowNodeInfo) {
        return new ApiResult<>(service.save(sysWorkflowNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流节点")
    @Operation(summary = "修改工作流节点", description = "工作流节点信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流节点信息", required = true)
            @RequestBody SysWorkflowNodeInfo sysWorkflowNodeInfo) {
        return new ApiResult<>(service.updateById(sysWorkflowNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流节点")
    @Operation(summary = "删除工作流节点", description = "删除工作流节点信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流节点id", required = true)
            @PathVariable("id") String id) {
        if (id.indexOf(".") > -1) {
            id = id.substring(id.indexOf(".") + 1);
        }
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流节点信息")
    @Operation(summary = "查询工作流节点信息", description = "通过主键查询工作流节点信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowNodeInfo> getById(
            @Parameter(description = "工作流节点id", required = true)
            @PathVariable("id") String id) {
        if (id.indexOf(".") > -1) {
            id = id.substring(id.indexOf(".") + 1);
        }
        SysWorkflowNodeInfo info = service.getById(id);
        if (info.getParentIds().endsWith(",")) {
            info.setParentIds(info.getParentIds().substring(0, info.getParentIds().length() - 1));
        }
        return new ApiResult<>(info);
    }

    @GetMapping("/tree")
    @Operation(summary = "工作流管理树", description = "工作流管理树")
    public ApiResult<List<SysWorkflowNodeTree>> tree(
            @Parameter(description = "工作流管理树查询类", required = false)
            SysWorkflowInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.tree(query));
    }

    @Operation(summary = "工作节点上级树", description = "工作节点上级树")
    @GetMapping("/parentNodes/{id}/{level}")
    public ApiResult<List<SysWorkflowNodeTree>> parentNodes(
            @Parameter(description = "工作流id", required = true) @PathVariable String id,
            @Parameter(description = "工作流节点层级", required = true) @PathVariable int level) {
        return new ApiResult<>(service.parentNodes(id, level));
    }
}