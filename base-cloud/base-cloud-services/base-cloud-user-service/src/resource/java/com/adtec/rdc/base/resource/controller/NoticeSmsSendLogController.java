package com.adtec.rdc.base.resource.controller;

import java.util.List;

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
import com.adtec.rdc.base.resource.model.po.NoticeSmsSendLog;
import com.adtec.rdc.base.resource.model.query.NoticeSmsSendLogQuery;
import com.adtec.rdc.base.resource.service.NoticeSmsSendLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
@RestController
@RequestMapping("/smslog")
@Api(value = "短信发送日志controller", tags = {"短信发送日志操作接口"})
public class NoticeSmsSendLogController {
	private static final String FUNC_NAME = "短信发送日志功能";
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private NoticeSmsSendLogService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短信发送日志")
    @ApiOperation(value = "添加短信发送日志", notes = "短信发送日志信息", httpMethod = "POST")
    @ApiImplicitParam(name = "smslog", value = "短信发送日志信息", required = true, dataType = "NoticeSmsSendLog")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody NoticeSmsSendLog smslog){
        return new ApiResult<>(service.save(smslog));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短信发送日志")
    @ApiOperation(value = "修改短信发送日志", notes = "短信发送日志信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "smslog", value = "短信发送日志信息", required = true, dataType = "NoticeSmsSendLog")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody NoticeSmsSendLog smslog){
        return new ApiResult<>(service.updateById(smslog));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短信发送日志")
    @ApiOperation(value = "删除短信发送日志", notes = "删除短信发送日志信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "短信发送日志id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }
    @ApiOperation(value = "批量短信发送日志", notes = "删除短信发送日志", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "短信发送日志ids", required = true, dataType = "list")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(@RequestBody List<String> ids){
        return new ApiResult<>(service.removeByIds(ids));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短信发送日志信息")
    @ApiOperation(value = "查询短信发送日志信息", notes = "通过主键查询短信发送日志信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "短信发送日志id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsSendLog> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "短信发送日志信息分页查询", notes = "短信发送日志信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "短信发送日志信息查询类", required = false, dataType = "NoticeSmsSendLogQuery")
    @GetMapping("/page")
    public ApiResult<NoticeSmsSendLogQuery> pageByQuery(NoticeSmsSendLogQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
