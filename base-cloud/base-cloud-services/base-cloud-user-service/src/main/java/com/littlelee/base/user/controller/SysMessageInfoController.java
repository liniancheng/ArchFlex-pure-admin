package com.littlelee.base.user.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysMessageInfo;
import com.littlelee.base.user.model.query.SysMessageInfoQuery;
import com.littlelee.base.user.service.SysMessageInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author littlelee
 * @date 2020-01-13 10:24:57
 */
@RestController
@RequestMapping("/message")
@Tag(name = "消息操作接口", description = "消息 controller")
public class SysMessageInfoController {

    private static final String FUNC_NAME = "消息功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysMessageInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除消息")
    @Operation(summary = "删除消息", description = "删除消息信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "消息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "标记已读")
    @Operation(summary = "标记已读", description = "通过主键查询消息信息，并标记已读")
    @GetMapping("/{id}")
    public ApiResult<SysMessageInfo> markRead(
            @Parameter(description = "消息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.markRead(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "批量标记已读")
    @Operation(summary = "批量标记已读", description = "通过主键查询消息信息，并标记已读")
    @PutMapping("/markread")
    public ApiResult<Void> markRead(
            @Parameter(description = "消息id列表", required = true)
            @RequestBody List<String> idList) {
        service.markRead(idList);
        return new ApiResult<>();
    }

    @Operation(summary = "消息信息分页查询", description = "消息信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysMessageInfoQuery> pageByQuery(
            SysMessageInfoQuery query) {
        query.setMessageRevUser(UserUtil.getUserId(request));
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}