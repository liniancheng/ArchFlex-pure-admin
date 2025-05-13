package com.adtec.rdc.base.resource. controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.resource.model.po.NoticeSmsSrvInfo;
import com.adtec.rdc.base.resource.model.query.NoticeSmsSrvInfoQuery;
import com.adtec.rdc.base.resource.service.NoticeSmsSrvInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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

/**
 * @author adtec
 * @date 2020-06-16 13:00:26
 */
@RestController
@RequestMapping("/smssrv")
@Api(value = "smssrvcontroller", tags = {"短息服务器操作接口"})
public class NoticeSmsSrvInfoController {
	private static final String FUNC_NAME = "短息服务器功能";
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private NoticeSmsSrvInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短息服务器")
    @ApiOperation(value = "添加短息服务器", notes = "短息服务器信息", httpMethod = "POST")
    @ApiImplicitParam(name = "smssrv", value = "短息服务器信息", required = true, dataType = "NoticeSmsSrvInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody NoticeSmsSrvInfo smssrv){
    	smssrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(smssrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短息服务器")
    @ApiOperation(value = "修改短息服务器", notes = "短息服务器信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "smssrv", value = "短息服务器信息", required = true, dataType = "NoticeSmsSrvInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody NoticeSmsSrvInfo smssrv){
    	smssrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(smssrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短息服务器")
    @ApiOperation(value = "删除短息服务器", notes = "删除短息服务器信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "短息服务器id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短息服务器信息")
    @ApiOperation(value = "查询短息服务器信息", notes = "通过主键查询短息服务器信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "smssrvid", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsSrvInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "短息服务器信息分页查询", notes = "短息服务器信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "短息服务器信息查询类", required = false, dataType = "NoticeSmsSrvInfoQuery")
    @GetMapping("/page")
    public ApiResult<NoticeSmsSrvInfoQuery> pageByQuery(NoticeSmsSrvInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
