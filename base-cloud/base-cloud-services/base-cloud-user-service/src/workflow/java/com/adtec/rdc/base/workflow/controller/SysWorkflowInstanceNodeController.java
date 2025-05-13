package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceNodeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
 * @date 2020-06-30 09:15:01
 */
@RestController
@RequestMapping("/workflowInstanceNode")
@Api(value = "工作流实例节点controller", tags = {"工作流实例节点操作接口"})
public class SysWorkflowInstanceNodeController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流实例节点功能";

    @Autowired
    private SysWorkflowInstanceNodeService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流实例节点")
    @ApiOperation(value = "添加工作流实例节点", notes = "工作流实例节点信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowInstanceNodeInfo", value = "工作流实例节点信息", required = true, dataType = "SysWorkflowInstanceNode")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowInstanceNode sysWorkflowInstanceNodeInfo){
        return new ApiResult<>(service.save(sysWorkflowInstanceNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流实例节点")
    @ApiOperation(value = "修改工作流实例节点", notes = "工作流实例节点信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysWorkflowInstanceNodeInfo", value = "工作流实例节点信息", required = true, dataType = "SysWorkflowInstanceNode")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowInstanceNode sysWorkflowInstanceNodeInfo){
        return new ApiResult<>(service.updateById(sysWorkflowInstanceNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流实例节点")
    @ApiOperation(value = "删除工作流实例节点", notes = "删除工作流实例节点信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流实例节点id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流实例节点信息")
    @ApiOperation(value = "查询工作流实例节点信息", notes = "通过主键查询工作流实例节点信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流实例节点id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstanceNode> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "工作流实例节点信息分页查询", notes = "工作流实例节点信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流实例节点信息查询类", required = false, dataType = "SysWorkflowInstanceNodeQuery")
    @GetMapping("/page")
    public ApiResult<SysWorkflowInstanceNodeQuery> pageByQuery(SysWorkflowInstanceNodeQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
