package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowNodeAuthQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowNodeAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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
 * @author adtec
 * @date 2020-06-30 09:14:34
 */
@RestController
@RequestMapping("/workflowNodeAuth")
@Api(value = "工作流节点权限controller", tags = {"工作流节点权限操作接口"})
public class SysWorkflowNodeAuthController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流节点权限功能";

    @Autowired
    private SysWorkflowNodeAuthService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流节点权限")
    @ApiOperation(value = "添加工作流节点权限", notes = "工作流节点权限信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowNodeAuth", value = "工作流节点权限信息", required = true, dataType = "SysWorkflowNodeAuth")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowNodeAuth sysWorkflowNodeAuth){
		if(sysWorkflowNodeAuth.getNodeId().indexOf(".")>-1) {
			sysWorkflowNodeAuth.setNodeId(sysWorkflowNodeAuth.getNodeId().substring(sysWorkflowNodeAuth.getNodeId().indexOf(".")+1));
		}
        return new ApiResult<>(service.save(sysWorkflowNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流节点权限")
    @ApiOperation(value = "修改工作流节点权限", notes = "工作流节点权限信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysWorkflowNodeAuth", value = "工作流节点权限信息", required = true, dataType = "SysWorkflowNodeAuth")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowNodeAuth sysWorkflowNodeAuth){
    	if(sysWorkflowNodeAuth.getNodeId().indexOf(".")>-1) {
			sysWorkflowNodeAuth.setNodeId(sysWorkflowNodeAuth.getNodeId().substring(sysWorkflowNodeAuth.getNodeId().indexOf(".")+1));
		}
    	return new ApiResult<>(service.updateById(sysWorkflowNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流节点权限")
    @ApiOperation(value = "删除工作流节点权限", notes = "删除工作流节点权限信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流节点权限id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流节点权限信息")
    @ApiOperation(value = "查询工作流节点权限信息", notes = "通过主键查询工作流节点权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流节点权限id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowNodeAuth> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "工作流节点权限信息分页查询", notes = "工作流节点权限信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流节点权限信息查询类", required = false, dataType = "SysWorkflowNodeAuthQuery")
    @GetMapping("/page")
    public ApiResult<SysWorkflowNodeAuthQuery> pageByQuery(SysWorkflowNodeAuthQuery query){
    	if(query.getNodeId().indexOf(".")>-1) {
    		query.setNodeId(query.getNodeId().substring(query.getNodeId().indexOf(".")+1));
		}
        return new ApiResult<>(service.pageByQuery(query));
    }
    
    @ApiOperation(value = "工作流节点权限角色查询", notes = "工作流节点权限角色查询", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流节点id", required = true, dataType = "string")
    @GetMapping("/roles/{id}")
    public ApiResult<List<SysRoleInfo>> roles(@PathVariable("id") String id){
        return new ApiResult<>(service.roles(id, UserUtil.getAppId(request)));
    }
    
    @ApiOperation(value = "工作流节点权限宏查询", notes = "工作流节点权限宏查询", httpMethod = "GET")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "workflowId", value = "工作流id", required = true, dataType = "string"),
        @ApiImplicitParam(name = "nodeId", value = "工作流节点id", required = true, dataType = "string"),
        @ApiImplicitParam(name = "authType", value = "授权类型", required = true, dataType = "string")
    })    
    @GetMapping("/macros/{workflowId}/{nodeId}/{authType}")
    public ApiResult<List<SysWorkflowMacroInfo>> macros(@PathVariable("workflowId") String workflowId, 
    		@PathVariable("nodeId") String nodeId, @PathVariable("authType") String authType){
        return new ApiResult<>(service.macros(workflowId, nodeId, authType));
    }
}
