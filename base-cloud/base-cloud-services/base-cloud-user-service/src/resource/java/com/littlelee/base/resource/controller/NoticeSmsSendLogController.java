package com.littlelee.base.resource.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.resource.model.po.NoticeSmsSendLog;
import com.littlelee.base.resource.model.query.NoticeSmsSendLogQuery;
import com.littlelee.base.resource.service.NoticeSmsSendLogService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author xinglj
 * @date 2020-06-15 12:13:02
 */
@RestController
@RequestMapping("/smslog")
@Tag(name = "短信发送日志操作接口", description = "短信发送日志相关操作")
public class NoticeSmsSendLogController {
    private static final String FUNC_NAME = "短信发送日志功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NoticeSmsSendLogService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短信发送日志")
    @Operation(summary = "添加短信发送日志", description = "新增一条短信发送日志")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "短信发送日志信息", required = true)
            @RequestBody NoticeSmsSendLog smslog) {
        return new ApiResult<>(service.save(smslog));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短信发送日志")
    @Operation(summary = "修改短信发送日志", description = "更新短信发送日志信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "短信发送日志信息", required = true)
            @RequestBody NoticeSmsSendLog smslog) {
        return new ApiResult<>(service.updateById(smslog));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短信发送日志")
    @Operation(summary = "删除短信发送日志", description = "根据主键删除短信发送日志")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "短信发送日志id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @Operation(summary = "批量删除短信发送日志", description = "根据多个主键批量删除短信发送日志")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(
            @Parameter(description = "短信发送日志id列表", required = true)
            @RequestBody List<String> ids) {
        return new ApiResult<>(service.removeByIds(ids));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短信发送日志信息")
    @Operation(summary = "查询短信发送日志信息", description = "根据主键查询单条短信发送日志")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsSendLog> getById(
            @Parameter(description = "短信发送日志id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "短信发送日志信息分页查询", description = "分页条件查询短信发送日志列表")
    @GetMapping("/page")
    public ApiResult<NoticeSmsSendLogQuery> pageByQuery(
            @Parameter(description = "查询条件", required = false)
            NoticeSmsSendLogQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
