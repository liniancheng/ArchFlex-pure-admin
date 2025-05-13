package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;
import com.adtec.rdc.base.workflow.model.query.SysUserTaskExtQuery;
import com.adtec.rdc.base.workflow.service.SysUserTaskExtService;

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
 * @date 2020-07-20 19:19:57
 */
@RestController
@RequestMapping("/userTaskExt")
@Api(value = "自定义任务controller", tags = {"自定义任务操作接口"})
public class SysUserTaskExtController extends BaseHttpController {
	private static final String FUNC_NAME = "自定义任务功能";

    @Autowired
    private SysUserTaskExtService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加自定义任务")
    @ApiOperation(value = "添加自定义任务", notes = "自定义任务信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUserTaskExt", value = "自定义任务信息", required = true, dataType = "SysUserTaskExt")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysUserTaskExt sysUserTaskExt){
    	sysUserTaskExt.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(sysUserTaskExt));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改自定义任务")
    @ApiOperation(value = "修改自定义任务", notes = "自定义任务信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysUserTaskExt", value = "自定义任务信息", required = true, dataType = "SysUserTaskExt")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysUserTaskExt sysUserTaskExt){
    	sysUserTaskExt.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(sysUserTaskExt));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除自定义任务")
    @ApiOperation(value = "删除自定义任务", notes = "删除自定义任务信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "自定义任务id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询自定义任务信息")
    @ApiOperation(value = "查询自定义任务信息", notes = "通过主键查询自定义任务信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "自定义任务id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysUserTaskExt> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "自定义任务信息分页查询", notes = "自定义任务信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "自定义任务信息查询类", required = false, dataType = "SysUserTaskExtQuery")
    @GetMapping("/page")
    public ApiResult<SysUserTaskExtQuery> pageByQuery(SysUserTaskExtQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
