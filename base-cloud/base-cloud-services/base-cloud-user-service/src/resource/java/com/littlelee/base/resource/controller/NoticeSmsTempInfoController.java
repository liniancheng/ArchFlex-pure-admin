package com.littlelee.base.resource.controller;

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
import com.littlelee.base.resource.model.po.NoticeSmsTempInfo;
import com.littlelee.base.resource.model.query.NoticeSmsTempInfoQuery;
import com.littlelee.base.resource.service.NoticeSmsTempInfoService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
@RestController
@RequestMapping("/smstemp")
@Tag(name = "短信模板操作接口", description = "短信模板相关操作")
public class NoticeSmsTempInfoController {
    private static final String FUNC_NAME = "短信模板功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private NoticeSmsTempInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短信模板")
    @Operation(summary = "添加短信模板", description = "新增短信模板信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "短信模板信息", required = true)
            @RequestBody NoticeSmsTempInfo smstemp) {
        smstemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(smstemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短信模板")
    @Operation(summary = "修改短信模板", description = "更新短信模板信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "短信模板信息", required = true)
            @RequestBody NoticeSmsTempInfo smstemp) {
        smstemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(smstemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短信模板")
    @Operation(summary = "删除短信模板", description = "根据主键删除短信模板")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "短信模板id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短信模板信息")
    @Operation(summary = "查询短信模板信息", description = "根据主键查询单条短信模板信息")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsTempInfo> getById(
            @Parameter(description = "短信模板id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "短信模板信息分页查询", description = "分页条件查询短信模板列表")
    @GetMapping("/page")
    public ApiResult<NoticeSmsTempInfoQuery> pageByQuery(
            @Parameter(description = "查询条件", required = false)
            NoticeSmsTempInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}