package com.littlelee.base.workflow.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysRoleInfo;
import com.littlelee.base.workflow.model.po.SysWorkflowMacroInfo;
import com.littlelee.base.workflow.model.po.SysWorkflowNodeAuth;
import com.littlelee.base.workflow.model.query.SysWorkflowNodeAuthQuery;
import com.littlelee.base.workflow.service.SysWorkflowNodeAuthService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
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
 * @date 2020-06-30 09:14:34
 */
@RestController
@RequestMapping("/workflowNodeAuth")
@Tag(name = "工作流节点权限操作接口", description = "工作流节点权限controller")
public class SysWorkflowNodeAuthController extends BaseHttpController {
    private static final String FUNC_NAME = "工作流节点权限功能";

    @Autowired
    private SysWorkflowNodeAuthService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加工作流节点权限")
    @Operation(summary = "添加工作流节点权限", description = "工作流节点权限信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "工作流节点权限信息", required = true)
            @RequestBody SysWorkflowNodeAuth sysWorkflowNodeAuth){
        if(sysWorkflowNodeAuth.getNodeId().indexOf(".")>-1) {
            sysWorkflowNodeAuth.setNodeId(sysWorkflowNodeAuth.getNodeId().substring(sysWorkflowNodeAuth.getNodeId().indexOf(".")+1));
        }
        return new ApiResult<>(service.save(sysWorkflowNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改工作流节点权限")
    @Operation(summary = "修改工作流节点权限", description = "工作流节点权限信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "工作流节点权限信息", required = true)
            @RequestBody SysWorkflowNodeAuth sysWorkflowNodeAuth){
        if(sysWorkflowNodeAuth.getNodeId().indexOf(".")>-1) {
            sysWorkflowNodeAuth.setNodeId(sysWorkflowNodeAuth.getNodeId().substring(sysWorkflowNodeAuth.getNodeId().indexOf(".")+1));
        }
        return new ApiResult<>(service.updateById(sysWorkflowNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除工作流节点权限")
    @Operation(summary = "删除工作流节点权限", description = "删除工作流节点权限信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "工作流节点权限id", required = true)
            @PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询工作流节点权限信息")
    @Operation(summary = "查询工作流节点权限信息", description = "通过主键查询工作流节点权限信息")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowNodeAuth> getById(
            @Parameter(description = "工作流节点权限id", required = true)
            @PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "工作流节点权限信息分页查询", description = "工作流节点权限信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysWorkflowNodeAuthQuery> pageByQuery(
            @Parameter(description = "工作流节点权限信息查询类", required = false)
            SysWorkflowNodeAuthQuery query){
        if(query.getNodeId().indexOf(".")>-1) {
            query.setNodeId(query.getNodeId().substring(query.getNodeId().indexOf(".")+1));
        }
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "工作流节点权限角色查询", description = "工作流节点权限角色查询")
    @GetMapping("/roles/{id}")
    public ApiResult<List<SysRoleInfo>> roles(
            @Parameter(description = "工作流节点id", required = true)
            @PathVariable("id") String id){
        return new ApiResult<>(service.roles(id, UserUtil.getAppId(request)));
    }

    @Operation(summary = "工作流节点权限宏查询", description = "工作流节点权限宏查询")
    @Parameters({
            @Parameter(name = "workflowId", description = "工作流id", required = true),
            @Parameter(name = "nodeId", description = "工作流节点id", required = true),
            @Parameter(name = "authType", description = "授权类型", required = true)
    })
    @GetMapping("/macros/{workflowId}/{nodeId}/{authType}")
    public ApiResult<List<SysWorkflowMacroInfo>> macros(
            @PathVariable String workflowId,
            @PathVariable String nodeId,
            @PathVariable String authType){
        return new ApiResult<>(service.macros(workflowId, nodeId, authType));
    }
}
