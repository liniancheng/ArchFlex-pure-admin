package com.adtec.rdc.base.user.controller;

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
import com.adtec.rdc.base.user.model.po.SysUserGroupInfo;
import com.adtec.rdc.base.user.model.query.SysUserGroupInfoQuery;
import com.adtec.rdc.base.user.service.SysUserGroupInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author liushp
 * @date 2019-12-04 16:05:39
 */
@RestController
@RequestMapping("/usergroup")
@Api(value = "用户组controller", tags = {"用户组操作接口"})
public class SysUserGroupInfoController {
	private static final String FUNC_NAME = "用户组功能";

    @Autowired
    private SysUserGroupInfoService service;
    @Autowired
	private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加用户组")
    @ApiOperation(value = "添加用户组", notes = "添加用户组", httpMethod = "POST")
    @ApiImplicitParam(name = "group", value = "用户组信息", required = true, dataType = "SysUserGroupInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysUserGroupInfo group){
    	group.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.saveGroup(group));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改用户组")
    @ApiOperation(value = "修改用户组", notes = "修改用户组", httpMethod = "PUT")
    @ApiImplicitParam(name = "group", value = "用户组信息", required = true, dataType = "SysUserGroupInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysUserGroupInfo group){
    	group.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateGroupById(group));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除用户组")
    @ApiOperation(value = "删除用户组", notes = "删除用户组信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "用户组id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询用户组信息")
    @ApiOperation(value = "查询用户组信息", notes = "通过主键查询用户组信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户组id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysUserGroupInfoQuery> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.findGroupListById(id));
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "用户组信息查询类")
    @ApiOperation(value = "用户组信息分页查询", notes = "用户组信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "用户组信息查询类", required = false, dataType = "SysUserGroupInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysUserGroupInfoQuery> pageByQuery(SysUserGroupInfoQuery query){
    	// query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
