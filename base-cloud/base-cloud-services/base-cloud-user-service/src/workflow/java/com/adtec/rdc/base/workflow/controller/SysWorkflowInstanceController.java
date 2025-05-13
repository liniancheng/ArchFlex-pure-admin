package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphBean;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowCreate;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInfoService;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceService;
import com.adtec.rdc.base.workflow.service.WorkflowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
 * @date 2020-06-30 09:15:15
 */
@RestController
@RequestMapping("/workflowInstance")
@Api(value = "工作流实例controller", tags = {"工作流实例操作接口"})
public class SysWorkflowInstanceController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流实例功能";

    @Autowired
    private SysWorkflowInstanceService service;
    @Autowired
    private SysWorkflowInfoService workflowInfoService;
    @Autowired
    private WorkflowService workflowService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流实例")
    @ApiOperation(value = "添加工作流实例", notes = "工作流实例信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowInstance", value = "工作流实例信息", required = true, dataType = "SysWorkflowInstance")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowInstance sysWorkflowInstance){
    	SysWorkflowInfo workflow = workflowInfoService.getById(sysWorkflowInstance.getWorkflowId());
    	WorkflowCreate workflowCreate = new WorkflowCreate();
    	workflowCreate.setAppId(UserUtil.getAppId(request));
    	workflowCreate.setUserId(UserUtil.getUserId(request));
    	workflowCreate.setWorkflowCode(workflow.getWorkflowCode());
    	workflowCreate.setInstanceName(sysWorkflowInstance.getInstanceName());
    	workflowService.create(workflowCreate);
        return new ApiResult<>(true);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流实例")
    @ApiOperation(value = "修改工作流实例", notes = "工作流实例信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysWorkflowInstance", value = "工作流实例信息", required = true, dataType = "SysWorkflowInstance")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowInstance sysWorkflowInstance){
        return new ApiResult<>(service.updateById(sysWorkflowInstance));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流实例")
    @ApiOperation(value = "删除工作流实例", notes = "删除工作流实例信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流实例信息")
    @ApiOperation(value = "查询工作流实例信息", notes = "通过主键查询工作流实例信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstance> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @GetMapping("/tree")
	@ApiOperation(value = "工作流实例管理树", notes = "工作流实例管理树", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流实例管理树查询类", required = false, dataType = "SysWorkflowInstanceQuery")
	public ApiResult<List<SysWorkflowTree>> tree(SysWorkflowInstanceQuery query) {
    	query.setAppId(UserUtil.getAppId(request));
    	return new ApiResult<>(service.tree(query));
	}
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "查询工作流实例进度图信息")
    @ApiOperation(value = "查询工作流实例进度图信息", notes = "查询工作流实例进度图信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流实例id", required = true, dataType = "string")
    @GetMapping("/graph/{id}")
    public ApiResult<MxGraphBean> graph(@PathVariable("id") String id){
        return new ApiResult<>(service.graph(id));
    }
}
