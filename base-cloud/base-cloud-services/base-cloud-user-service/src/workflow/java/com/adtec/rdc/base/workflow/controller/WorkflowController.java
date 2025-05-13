package com.adtec.rdc.base.workflow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowCreate;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.WorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceService;
import com.adtec.rdc.base.workflow.service.WorkflowService;
import com.alibaba.druid.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 工作流对外服务接口
 * @author JTao
 *
 */
@RestController
@RequestMapping("/workflows")
@Api(value = "工作流controller", tags = {"工作流操作接口"})
public class WorkflowController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流";
	
	@Autowired
    private WorkflowService service;
	@Autowired
    private SysWorkflowInstanceService instanceService;
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "创建工作流实例")
    @ApiOperation(value = "创建工作流实例", notes = "工作流信息", httpMethod = "POST")
	@ApiImplicitParam(name = "workflowCreate", value = "工作流", required = true, dataType = "WorkflowCreate")
	@PostMapping("/create")
    public ApiResult<String> create(@RequestBody WorkflowCreate workflowCreate){
        return new ApiResult<>(service.create(workflowCreate));
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "查询工作流信息")
    @ApiOperation(value = "查询工作流信息", notes = "查询工作流信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<WorkflowQuery> query(@PathVariable("id") String id){
        return new ApiResult<>(service.query(id));
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流")
    @ApiOperation(value = "删除工作流", notes = "删除工作流信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(instanceService.removeById(id));
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "批量查询工作流状态")
    @ApiOperation(value = "批量查询工作流状态", notes = "批量查询工作流状态", httpMethod = "GET")
    @ApiImplicitParam(name = "ids", value = "工作流实例id列表", required = true, allowMultiple = true, dataType = "string")
    @PostMapping("/status")
    public ApiResult<List<WorkflowQuery>> status(@RequestBody List<String> ids){
        return new ApiResult<>(service.status(ids));
    }
	
	@PostMapping("/callback")
    public ApiResult<Boolean> callback(@RequestBody WorkflowQuery workflow){
		System.out.println(workflow);
        return new ApiResult<>(true);
    }
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "查询工作流信息-OA使用")
    @ApiOperation(value = "查询工作流信息-OA使用", notes = "查询工作流信息-OA使用", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @PostMapping("/tasks")
    public ApiResult<WorkflowInfoQuery> tasks(@RequestBody WorkflowInfoQuery query){
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
	@ApiOperation(value = "用户任务审批-OA使用", notes = "用户任务审批-OA使用", httpMethod = "POST")
	@ApiImplicitParam(name = "workflow", value = "工作流信息", required = true, dataType = "Workflow")
	@PostMapping("/operTask")
	public ApiResult<SysWorkflowInstanceNodeOper> operTask(@RequestBody Workflow workflow) {
		return new ApiResult<>(service.update(workflow, null, workflow.getOperUser()));
	}
}
