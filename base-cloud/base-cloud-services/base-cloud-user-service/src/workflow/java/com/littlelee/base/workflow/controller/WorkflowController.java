package com.littlelee.base.workflow.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.workflow.feign.bo.WorkflowCreate;
import com.littlelee.base.workflow.feign.bo.WorkflowQuery;
import com.littlelee.base.workflow.model.bo.Workflow;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.WorkflowInfoQuery;
import com.littlelee.base.workflow.service.SysWorkflowInstanceService;
import com.littlelee.base.workflow.service.WorkflowService;
import com.alibaba.druid.util.StringUtils;

import io.swagger.v3.oas.annotations.Operation;

/**
 * 工作流对外服务接口
 * @author littlelee
 *
 */
@RestController
@RequestMapping("/workflows")
@Tag(name = "工作流操作接口", description = "工作流对外服务接口")
public class WorkflowController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流";

    @Autowired
    private WorkflowService service;
    @Autowired
    private SysWorkflowInstanceService instanceService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "创建工作流实例")
    @Operation(summary = "创建工作流实例", description = "根据传入参数创建工作流实例")
    @PostMapping("/create")
    public ApiResult<String> create(
            @Parameter(description = "工作流创建信息", required = true)
            @RequestBody WorkflowCreate workflowCreate) {
        return new ApiResult<>(service.create(workflowCreate));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "查询工作流信息")
    @Operation(summary = "查询工作流信息", description = "根据实例 id 查询工作流详情")
    @GetMapping("/{id}")
    public ApiResult<WorkflowQuery> query(
            @Parameter(description = "工作流实例 id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.query(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流")
    @Operation(summary = "删除工作流", description = "根据实例 id 删除工作流")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流实例 id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(instanceService.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "批量查询工作流状态")
    @Operation(summary = "批量查询工作流状态", description = "根据实例 id 列表批量查询状态")
    @PostMapping("/status")
    public ApiResult<List<WorkflowQuery>> status(
            @Parameter(description = "工作流实例 id 列表", required = true)
            @RequestBody List<String> ids) {
        return new ApiResult<>(service.status(ids));
    }

    @PostMapping("/callback")
    public ApiResult<Boolean> callback(
            @Parameter(description = "回调工作流信息", required = true)
            @RequestBody WorkflowQuery workflow) {
        System.out.println(workflow);
        return new ApiResult<>(true);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "查询工作流信息-OA使用")
    @Operation(summary = "查询工作流信息-OA使用", description = "OA 专用分页查询用户任务列表")
    @PostMapping("/tasks")
    public ApiResult<WorkflowInfoQuery> tasks(
            @Parameter(description = "查询参数", required = true)
            @RequestBody WorkflowInfoQuery query) {
        if (query == null) {
            return new ApiResult().failed("参数为空，请检查！");
        }
        if (StringUtils.isEmpty(query.getAppId())) {
            return new ApiResult().failed("appId为空，请检查！");
        }
        if (StringUtils.isEmpty(query.getLoginName())) {
            return new ApiResult().failed("登录名为空，请检查！");
        }
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "用户任务审批-OA使用", description = "OA 专用任务审批")
    @PostMapping("/operTask")
    public ApiResult<SysWorkflowInstanceNodeOper> operTask(
            @Parameter(description = "任务审批信息", required = true)
            @RequestBody Workflow workflow) {
        return new ApiResult<>(service.update(workflow, null, workflow.getOperUser()));
    }
}
