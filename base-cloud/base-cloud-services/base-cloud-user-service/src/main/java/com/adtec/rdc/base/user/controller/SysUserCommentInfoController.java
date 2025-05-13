package com.adtec.rdc.base.user.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysUserCommentInfo;
import com.adtec.rdc.base.user.model.query.SysUserCommentInfoQuery;
import com.adtec.rdc.base.user.service.SysUserCommentInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;

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
 * @date 2020-07-28 15:38:50
 */
@RestController
@RequestMapping("/comment")
@Api(value = "用户评论管理controller", tags = {"用户评论管理操作接口"})
public class SysUserCommentInfoController extends BaseHttpController {
	private static final String FUNC_NAME = "用户评论管理功能";

    @Autowired
    private SysUserCommentInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加用户评论管理")
    @ApiOperation(value = "添加用户评论管理", notes = "用户评论管理信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUserCommentInfo", value = "用户评论管理信息", required = true, dataType = "SysUserCommentInfo")
    @PostMapping
    public ApiResult<SysUserCommentInfo> save(@RequestBody SysUserCommentInfo sysUserCommentInfo){
    	sysUserCommentInfo.setAppId(UserUtil.getAppId(request));
    	sysUserCommentInfo.setCreateUser(UserUtil.getUserId(request));
    	sysUserCommentInfo.setCreateTime(LocalDateTime.now());
    	sysUserCommentInfo.setMyComment(true);
        return new ApiResult<>(service.saveComment(sysUserCommentInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改用户评论管理")
    @ApiOperation(value = "修改用户评论管理", notes = "用户评论管理信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysUserCommentInfo", value = "用户评论管理信息", required = true, dataType = "SysUserCommentInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysUserCommentInfo sysUserCommentInfo){
        return new ApiResult<>(service.updateById(sysUserCommentInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除用户评论管理")
    @ApiOperation(value = "删除用户评论管理", notes = "删除用户评论管理信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "用户评论管理id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询用户评论管理信息")
    @ApiOperation(value = "查询用户评论管理信息", notes = "通过主键查询用户评论管理信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户评论管理id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysUserCommentInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "用户评论管理信息分页查询", notes = "用户评论管理信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "用户评论管理信息查询类", required = false, dataType = "SysUserCommentInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysUserCommentInfoQuery> pageByQuery(SysUserCommentInfoQuery query){
    	query.setCurrentUserId(UserUtil.getUserId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
