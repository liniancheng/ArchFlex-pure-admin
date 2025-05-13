package com.adtec.rdc.base.param.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.param.model.po.SysParamInfo;
import com.adtec.rdc.base.param.query.SysParamInfoQuery;
import com.adtec.rdc.base.param.service.SysParamInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/param")
@Api(value = "参数信息controller", tags = {"参数信息操作接口"})
public class SysParamInfoController {
	
	private static final String FUNC_NAME = "参数功能";

	@Autowired
	private SysParamInfoService service;
	@Autowired
	private HttpServletRequest request;
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加参数")
    @ApiOperation(value = "添加参数", notes = "参数信息", httpMethod = "POST")
    @ApiImplicitParam(name = "param", value = "参数信息", required = true, dataType = "SysParamInfo")
    @PostMapping
	public ApiResult<Boolean> save(@RequestBody SysParamInfo param){
		String parentAppId = UserUtil.getAppId(request);
		param.setAppId(parentAppId);
		return new ApiResult<>(service.onlySave(param));
	}
	
	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改参数")
    @ApiOperation(value = "修改参数", notes = "参数信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "param", value = "参数信息", required = true, dataType = "SysParamInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysParamInfo param){
        return new ApiResult<>(service.onlyUpdate(param));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除参数")
    @ApiOperation(value = "删除参数", notes = "删除参数信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "参数id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询参数信息")
    @ApiOperation(value = "查询参数信息", notes = "通过主键查询参数信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "参数id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysParamInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "参数信息分页查询", notes = "参数信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "参数信息查询类", required = false, dataType = "SysParamInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysParamInfoQuery> pageByQuery(SysParamInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
    @ApiOperation(value = "根据参数标识获取参数值", notes = "根据参数标识获取参数值", httpMethod = "GET")
    @ApiImplicitParam(name = "paramName", value = "参数标识", required = true, dataType = "string")
    @GetMapping("/loadParam/{appId}/{paramName}")
    public String loadParam(@PathVariable("appId")String appId, @PathVariable("paramName") String paramName){
    	return service.getParam(paramName, Optional.ofNullable(UserUtil.getAppId(request)).orElse(appId));
    }	
}
