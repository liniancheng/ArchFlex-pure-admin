package com.adtec.rdc.base.layout.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.adtec.rdc.base.layout.model.query.SysLayoutInfoQuery;
import com.adtec.rdc.base.layout.service.SysLayoutInfoService;

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
 * @author dengchf
 * @date 2020-08-25 15:04:09
 */
@RestController
@RequestMapping("/layout")
@Api(value = "首页布局controller", tags = {"首页布局操作接口"})
public class SysLayoutInfoController extends BaseHttpController{
	private static final String FUNC_NAME = "首页布局功能";

    @Autowired
    private SysLayoutInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加首页布局")
    @ApiOperation(value = "添加首页布局", notes = "首页布局信息", httpMethod = "POST")
    @ApiImplicitParam(name = "layout", value = "首页布局信息", required = true, dataType = "SysLayoutInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysLayoutInfo layout){
    	layout.setAppId(UserUtil.getAppId(request));
    	layout.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.save(layout));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改首页布局")
    @ApiOperation(value = "修改首页布局", notes = "首页布局信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "layout", value = "首页布局信息", required = true, dataType = "SysLayoutInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysLayoutInfo layout){
    	layout.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(layout));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除首页布局")
    @ApiOperation(value = "删除首页布局", notes = "删除首页布局信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "首页布局id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询首页布局信息")
    @ApiOperation(value = "查询首页布局信息", notes = "通过主键查询首页布局信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "首页布局id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysLayoutInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "首页布局信息分页查询", notes = "首页布局信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "首页布局信息查询类", required = false, dataType = "SysLayoutInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysLayoutInfoQuery> pageByQuery(SysLayoutInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
    
    @ApiOperation(value = "布局预览", notes = "布局预览", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "布局id", required = false, dataType = "String")
    @GetMapping("/fetchOnce/{id}")
    public ApiResult<SysLayoutInfo> fetchOnce(@PathVariable("id") String id){
        return new ApiResult<>(service.fetchOnce(id, UserUtil.getAppId(request)));
    }
    @ApiOperation(value = "获取用户自定义布局", notes = "获取用户自定义布局", httpMethod = "GET")
    @GetMapping("/fetchByLoginName")
    public ApiResult<SysLayoutInfo> fetchByLoginName(){
        return new ApiResult<>(service.fetchByLoginName(UserUtil.getLoginName(request), UserUtil.getAppId(request)));
    }
    @ApiOperation(value = "加载布局", notes = "加载布局", httpMethod = "GET")
    @GetMapping("/fetchLayout")
    public ApiResult<SysLayoutInfo> fetchLayout(){
        return new ApiResult<>(service.fetchLayout(UserUtil.getLoginName(request), UserUtil.getAppId(request), UserUtil.getRoleIds(request)));
    }
}
