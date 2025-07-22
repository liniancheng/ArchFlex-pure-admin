package com.littlelee.base.resource. controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.resource.model.po.NoticeSmsSrvInfo;
import com.littlelee.base.resource.model.query.NoticeSmsSrvInfoQuery;
import com.littlelee.base.resource.service.NoticeSmsSrvInfoService;

import io.swagger.v3.oas.annotations.Operation;

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

/**
 * @author littlelee
 * @date 2020-06-16 13:00:26
 */
@RestController
@RequestMapping("/smssrv")
@Tag(name = "短信服务器操作接口", description = "短信服务器相关操作")
public class NoticeSmsSrvInfoController {
    private static final String FUNC_NAME = "短信服务器功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NoticeSmsSrvInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短信服务器")
    @Operation(summary = "添加短信服务器", description = "新增短信服务器信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "短信服务器信息", required = true)
            @RequestBody NoticeSmsSrvInfo smssrv) {
        smssrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(smssrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短信服务器")
    @Operation(summary = "修改短信服务器", description = "更新短信服务器信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "短信服务器信息", required = true)
            @RequestBody NoticeSmsSrvInfo smssrv) {
        smssrv.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(smssrv));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短信服务器")
    @Operation(summary = "删除短信服务器", description = "根据主键删除短信服务器")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "短信服务器id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短信服务器信息")
    @Operation(summary = "查询短信服务器信息", description = "根据主键查询单条短信服务器信息")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsSrvInfo> getById(
            @Parameter(description = "短信服务器id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "短信服务器信息分页查询", description = "分页条件查询短信服务器列表")
    @GetMapping("/page")
    public ApiResult<NoticeSmsSrvInfoQuery> pageByQuery(
            @Parameter(description = "查询条件") NoticeSmsSrvInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}
