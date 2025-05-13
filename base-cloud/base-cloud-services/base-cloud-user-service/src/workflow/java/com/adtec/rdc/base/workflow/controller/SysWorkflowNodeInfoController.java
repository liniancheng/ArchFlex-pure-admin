package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowNodeTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowNodeInfoService;

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
 * @date 2020-06-30 09:14:21
 */
@RestController
@RequestMapping("/workflowNode")
@Api(value = "工作流节点controller", tags = {"工作流节点操作接口"})
public class SysWorkflowNodeInfoController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流节点功能";

    @Autowired
    private SysWorkflowNodeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流节点")
    @ApiOperation(value = "添加工作流节点", notes = "工作流节点信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowNodeInfo", value = "工作流节点信息", required = true, dataType = "SysWorkflowNodeInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowNodeInfo sysWorkflowNodeInfo){
        return new ApiResult<>(service.save(sysWorkflowNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流节点")
    @ApiOperation(value = "修改工作流节点", notes = "工作流节点信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysWorkflowNodeInfo", value = "工作流节点信息", required = true, dataType = "SysWorkflowNodeInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowNodeInfo sysWorkflowNodeInfo){
        return new ApiResult<>(service.updateById(sysWorkflowNodeInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流节点")
    @ApiOperation(value = "删除工作流节点", notes = "删除工作流节点信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流节点id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
    	if(id.indexOf(".")>-1) {
    		id = id.substring(id.indexOf(".")+1);
    	}
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流节点信息")
    @ApiOperation(value = "查询工作流节点信息", notes = "通过主键查询工作流节点信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流节点id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowNodeInfo> getById(@PathVariable("id") String id){
    	if(id.indexOf(".")>-1) {
    		id = id.substring(id.indexOf(".")+1);
    	}
    	SysWorkflowNodeInfo info = service.getById(id);
    	if(info.getParentIds().endsWith(",")) {
    		info.setParentIds(info.getParentIds().substring(0, info.getParentIds().length()-1));
    	}
        return new ApiResult<>(info);
    }

    @GetMapping("/tree")
	@ApiOperation(value = "工作流管理树", notes = "工作流管理树", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流管理树查询类", required = false, dataType = "SysWorkflowInfoQuery")
	public ApiResult<List<SysWorkflowNodeTree>> tree(SysWorkflowInfoQuery query) {
    	query.setAppId(UserUtil.getAppId(request));
    	return new ApiResult<>(service.tree(query));
	}
    
    @ApiOperation(value = "工作节点上级树", notes = "工作节点上级树", httpMethod = "GET")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "工作流id", required = true, dataType = "string"),
    	@ApiImplicitParam(name = "level", value = "工作流节点层级", required = true, dataType = "int")
    })
    @GetMapping("/parentNodes/{id}/{level}")
	public ApiResult<List<SysWorkflowNodeTree>> parentNodes(@PathVariable("id") String id, @PathVariable("level") int level) {
    	return new ApiResult<>(service.parentNodes(id, level));
	}
}
