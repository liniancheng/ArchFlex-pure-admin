package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceNodeAuthQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceNodeAuthService;

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
 * @date 2020-07-02 10:06:37
 */
@RestController
@RequestMapping("/SysWorkflowInstanceNodeAuth")
@Api(value = "流程节点权限controller", tags = {"流程节点权限操作接口"})
public class SysWorkflowInstanceNodeAuthController {
	private static final String FUNC_NAME = "流程节点权限功能";

    @Autowired
    private SysWorkflowInstanceNodeAuthService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加流程节点权限")
    @ApiOperation(value = "添加流程节点权限", notes = "流程节点权限信息", httpMethod = "POST")
    @ApiImplicitParam(name = "SysWorkflowInstanceNodeAuth", value = "流程节点权限信息", required = true, dataType = "SysWorkflowInstanceNodeAuth")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowInstanceNodeAuth SysWorkflowInstanceNodeAuth){
        return new ApiResult<>(service.save(SysWorkflowInstanceNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改流程节点权限")
    @ApiOperation(value = "修改流程节点权限", notes = "流程节点权限信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "SysWorkflowInstanceNodeAuth", value = "流程节点权限信息", required = true, dataType = "SysWorkflowInstanceNodeAuth")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowInstanceNodeAuth SysWorkflowInstanceNodeAuth){
        return new ApiResult<>(service.updateById(SysWorkflowInstanceNodeAuth));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除流程节点权限")
    @ApiOperation(value = "删除流程节点权限", notes = "删除流程节点权限信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "流程节点权限id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询流程节点权限信息")
    @ApiOperation(value = "查询流程节点权限信息", notes = "通过主键查询流程节点权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "流程节点权限id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInstanceNodeAuth> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "流程节点权限信息分页查询", notes = "流程节点权限信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "流程节点权限信息查询类", required = false, dataType = "SysWorkflowInstanceNodeAuthQuery")
    @GetMapping("/page")
    public ApiResult<SysWorkflowInstanceNodeAuthQuery> pageByQuery(SysWorkflowInstanceNodeAuthQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
