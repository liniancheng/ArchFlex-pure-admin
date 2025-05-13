package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowMacroTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowMacroInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowMacroInfoService;

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
 * @date 2020-07-05 07:55:01
 */
@RestController
@RequestMapping("/workflowMacro")
@Api(value = "工作流宏变量controller", tags = {"工作流宏变量操作接口"})
public class SysWorkflowMacroInfoController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流宏变量功能";

    @Autowired
    private SysWorkflowMacroInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流宏变量")
    @ApiOperation(value = "添加工作流宏变量", notes = "工作流宏变量信息", httpMethod = "POST")
    @ApiImplicitParam(name = "SysWorkflowMacroInfo", value = "工作流宏变量信息", required = true, dataType = "SysWorkflowMacroInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowMacroInfo SysWorkflowMacroInfo){
        return new ApiResult<>(service.save(SysWorkflowMacroInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流宏变量")
    @ApiOperation(value = "修改工作流宏变量", notes = "工作流宏变量信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "SysWorkflowMacroInfo", value = "工作流宏变量信息", required = true, dataType = "SysWorkflowMacroInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowMacroInfo SysWorkflowMacroInfo){
        return new ApiResult<>(service.updateById(SysWorkflowMacroInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流宏变量")
    @ApiOperation(value = "删除工作流宏变量", notes = "删除工作流宏变量信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流宏变量id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流宏变量信息")
    @ApiOperation(value = "查询工作流宏变量信息", notes = "通过主键查询工作流宏变量信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流宏变量id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowMacroInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "工作流宏变量信息分页查询", notes = "工作流宏变量信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流宏变量信息查询类", required = false, dataType = "SysWorkflowMacroInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysWorkflowMacroInfoQuery> pageByQuery(SysWorkflowMacroInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
    
    @GetMapping("/tree")
	@ApiOperation(value = "工作流宏变量管理树", notes = "工作流宏变量管理树", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流宏变量管理树查询类", required = false, dataType = "SysWorkflowMacroInfoQuery")
	public ApiResult<List<SysWorkflowMacroTree>> tree(SysWorkflowMacroInfoQuery query) {
    	return new ApiResult<>(service.tree(query));
	}

}
