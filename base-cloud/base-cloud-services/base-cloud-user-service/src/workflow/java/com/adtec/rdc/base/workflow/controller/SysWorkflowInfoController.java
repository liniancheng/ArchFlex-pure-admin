package com.adtec.rdc.base.workflow.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowCopy;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInfoService;

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
 * @date 2020-06-30 09:15:26
 */
@RestController
@RequestMapping("/workflow")
@Api(value = "工作流controller", tags = {"工作流操作接口"})
public class SysWorkflowInfoController extends BaseHttpController {
	private static final String FUNC_NAME = "工作流功能";

    @Autowired
    private SysWorkflowInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加工作流")
    @ApiOperation(value = "添加工作流", notes = "工作流信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowInfo", value = "工作流信息", required = true, dataType = "SysWorkflowInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysWorkflowInfo sysWorkflowInfo){
    	sysWorkflowInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(sysWorkflowInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改工作流")
    @ApiOperation(value = "修改工作流", notes = "工作流信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysWorkflowInfo", value = "工作流信息", required = true, dataType = "SysWorkflowInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysWorkflowInfo sysWorkflowInfo){
    	sysWorkflowInfo.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(sysWorkflowInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除工作流")
    @ApiOperation(value = "删除工作流", notes = "删除工作流信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "工作流id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询工作流信息")
    @ApiOperation(value = "查询工作流信息", notes = "通过主键查询工作流信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "工作流id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysWorkflowInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @GetMapping("/tree")
	@ApiOperation(value = "工作流管理树", notes = "工作流管理树", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "工作流管理树查询类", required = false, dataType = "SysWorkflowInfoQuery")
	public ApiResult<List<SysWorkflowTree>> tree(SysWorkflowInfoQuery query) {
    	query.setAppId(UserUtil.getAppId(request));
    	return new ApiResult<>(service.tree(query));
	}
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "复制工作流")
    @ApiOperation(value = "复制工作流", notes = "工作流信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysWorkflowCopy", value = "工作流信息", required = true, dataType = "SysWorkflowCopy")
    @PostMapping("/copy")
    public ApiResult<Boolean> copy(@RequestBody SysWorkflowCopy sysWorkflowCopy){
        return new ApiResult<>(service.copy(sysWorkflowCopy));
    }

}
