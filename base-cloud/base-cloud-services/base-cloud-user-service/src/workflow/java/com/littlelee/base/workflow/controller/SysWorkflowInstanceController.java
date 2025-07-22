package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.bo.mxgraph.MxGraphBean;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.workflow.feign.bo.WorkflowCreate;
import com.littlelee.base.workflow.model.bo.SysWorkflowTree;
import com.littlelee.base.workflow.model.po.SysWorkflowInfo;
import com.littlelee.base.workflow.model.po.SysWorkflowInstance;
import com.littlelee.base.workflow.model.query.SysWorkflowInstanceQuery;
import com.littlelee.base.workflow.service.SysWorkflowInfoService;
import com.littlelee.base.workflow.service.SysWorkflowInstanceService;
import com.littlelee.base.workflow.service.WorkflowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author littlelee
 * @date 2020-06-30 09:15:15
 */
@RestController
@RequestMapping("/workflowInstance")
@Tag(name = "工作流实例操作接口", description = "工作流实例controller")
public class SysWorkflowInstanceController extends BaseHttpController {

    private static final String FUNC_NAME = "工作流实例功能";

    @Autowired
    private SysWorkflowInstanceService service;
    @Autowired
    private SysWorkflowInfoService workflowInfoService;
    @Autowired
    private WorkflowService workflowService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流实例")
    @Operation(summary = "添加工作流实例", description = "工作流实例信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流实例信息", required = true)
            @RequestBody SysWorkflowInstance sysWorkflowInstance) {
        SysWorkflowInfo workflow = workflowInfoService.getById(sysWorkflowInstance.getWorkflowId());
        WorkflowCreate workflowCreate = new WorkflowCreate();
        workflowCreate.setAppId(UserUtil.getAppId(request));
        workflowCreate.setUserId(UserUtil.getUserId(request));
        workflowCreate.setWorkflowCode(workflow.getWorkflowCode());
        workflowCreate.setInstanceName(sysWorkflowInstance.getInstanceName());
        workflowService.create(workflowCreate);
        return new ApiResult<>(true);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流实例")
    @Operation(summary = "修改工作流实例", description = "工作流实例信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流实例信息", required = true)
            @RequestBody SysWorkflowInstance sysWorkflowInstance) {
        return new ApiResult<>(service.updateById(sysWorkflowInstance));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流实例")
    @Operation(summary = "删除工作流实例", description = "删除工作流实例信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流实例id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流实例信息")
    @Operation(summary = "查询工作流实例信息", description = "通过主键查询工作流实例信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstance> getById(
            @Parameter(description = "工作流实例id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @GetMapping("/tree")
    @Operation(summary = "工作流实例管理树", description = "工作流实例管理树")
    public ApiResult<List<SysWorkflowTree>> tree(
            @Parameter(description = "工作流实例管理树查询类", required = false)
            SysWorkflowInstanceQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.tree(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "查询工作流实例进度图信息")
    @Operation(summary = "查询工作流实例进度图信息", description = "查询工作流实例进度图信息")
    @GetMapping("/graph/{id}")
    public ApiResult<MxGraphBean> graph(
            @Parameter(description = "工作流实例id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.graph(id));
    }
}