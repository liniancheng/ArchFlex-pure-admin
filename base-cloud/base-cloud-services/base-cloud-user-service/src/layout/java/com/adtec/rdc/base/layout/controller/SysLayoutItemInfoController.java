package com.adtec.rdc.base.layout.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.layout.model.po.SysLayoutItemInfo;
import com.adtec.rdc.base.layout.model.query.SysLayoutItemInfoQuery;
import com.adtec.rdc.base.layout.service.SysLayoutItemInfoService;

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
 * @date 2020-08-25 15:17:06
 */
@RestController
@RequestMapping("/layoutItem")
@Api(value = "布局数据项controller", tags = {"布局数据项操作接口"})
public class SysLayoutItemInfoController extends BaseHttpController{
	private static final String FUNC_NAME = "布局数据项功能";

    @Autowired
    private SysLayoutItemInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加布局数据项")
    @ApiOperation(value = "添加布局数据项", notes = "布局数据项信息", httpMethod = "POST")
    @ApiImplicitParam(name = "layoutItem", value = "布局数据项信息", required = true, dataType = "SysLayoutItemInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysLayoutItemInfo layoutItem){
    	layoutItem.setAppId(UserUtil.getAppId(request));
    	layoutItem.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.save(layoutItem));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改布局数据项")
    @ApiOperation(value = "修改布局数据项", notes = "布局数据项信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "layoutItem", value = "布局数据项信息", required = true, dataType = "SysLayoutItemInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysLayoutItemInfo layoutItem){
    	layoutItem.setAppId(UserUtil.getAppId(request));
    	layoutItem.setLoginName(UserUtil.getLoginName(request));
        return new ApiResult<>(service.updateById(layoutItem));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除布局数据项")
    @ApiOperation(value = "删除布局数据项", notes = "删除布局数据项信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "布局数据项id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询布局数据项信息")
    @ApiOperation(value = "查询布局数据项信息", notes = "通过主键查询布局数据项信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "布局数据项id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysLayoutItemInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "布局数据项信息分页查询", notes = "布局数据项信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "布局数据项信息查询类", required = false, dataType = "SysLayoutItemInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysLayoutItemInfoQuery> pageByQuery(SysLayoutItemInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
    @ApiOperation(value = "用户具备权限的布局数据项信息分页查询", notes = "用户具备权限的布局数据项信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "用户具备权限的布局数据项信息查询类", required = false, dataType = "SysLayoutItemInfoQuery")
    @GetMapping("/fetchPerson")
    public ApiResult<SysLayoutItemInfoQuery> fetchPerson(SysLayoutItemInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.fetchPerson(query, UserUtil.getRoleIds(request)));
    }	
}
