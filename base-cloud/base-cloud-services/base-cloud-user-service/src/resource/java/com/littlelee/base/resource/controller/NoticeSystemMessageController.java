package com.littlelee.base.resource.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.littlelee.base.resource.model.po.NoticeSystemMessage;
import com.littlelee.base.resource.model.query.NoticeSystemMessageQuery;
import com.littlelee.base.resource.service.NoticeSystemMessageService;

import io.swagger.v3.oas.annotations.Operation;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
@RestController
@RequestMapping("/sysmsg")
@Tag(name = "系统消息操作接口", description = "系统消息相关操作")
public class NoticeSystemMessageController {
    private static final String FUNC_NAME = "系统消息功能";

    @Autowired
    private NoticeSystemMessageService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加系统消息")
    @Operation(summary = "添加系统消息", description = "新增系统消息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "系统消息信息", required = true)
            @RequestBody NoticeSystemMessage sysmsg) {
        return new ApiResult<>(service.save(sysmsg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改系统消息")
    @Operation(summary = "修改系统消息", description = "更新系统消息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "系统消息信息", required = true)
            @RequestBody NoticeSystemMessage sysmsg) {
        return new ApiResult<>(service.updateById(sysmsg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除系统消息")
    @Operation(summary = "删除系统消息", description = "根据主键删除系统消息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "系统消息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @Operation(summary = "批量删除系统消息", description = "批量删除系统消息")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(
            @Parameter(description = "系统消息id列表", required = true)
            @RequestBody List<String> ids) {
        return new ApiResult<>(service.removeByIds(ids));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询系统消息信息")
    @Operation(summary = "查询系统消息信息", description = "根据主键查询单条系统消息")
    @GetMapping("/{id}")
    public ApiResult<NoticeSystemMessage> getById(
            @Parameter(description = "系统消息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "系统消息信息分页查询", description = "分页条件查询系统消息列表")
    @GetMapping("/page")
    public ApiResult<NoticeSystemMessageQuery> pageByQuery(
            @Parameter(description = "查询条件", required = false)
            NoticeSystemMessageQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }
}
