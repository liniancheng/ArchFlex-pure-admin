package com.littlelee.base.param.controller;

import java.util.Optional;

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
import com.littlelee.base.param.model.po.SysParamInfo;
import com.littlelee.base.param.query.SysParamInfoQuery;
import com.littlelee.base.param.service.SysParamInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/param")
@Tag(name = "参数信息操作接口", description = "参数信息的增删改查")
public class SysParamInfoController {

    private static final String FUNC_NAME = "参数功能";

    @Autowired
    private SysParamInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加参数")
    @Operation(summary = "添加参数", description = "新增一条参数记录")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "参数信息", required = true)
            @RequestBody SysParamInfo param) {
        String parentAppId = UserUtil.getAppId(request);
        param.setAppId(parentAppId);
        return new ApiResult<>(service.onlySave(param));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改参数")
    @Operation(summary = "修改参数", description = "更新已有参数记录")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "参数信息", required = true)
            @RequestBody SysParamInfo param) {
        return new ApiResult<>(service.onlyUpdate(param));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除参数")
    @Operation(summary = "删除参数", description = "根据主键删除参数")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "参数主键", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询参数信息")
    @Operation(summary = "查询参数信息", description = "根据主键查询单条参数记录")
    @GetMapping("/{id}")
    public ApiResult<SysParamInfo> getById(
            @Parameter(description = "参数主键", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "参数信息分页查询", description = "分页+条件查询参数列表")
    @GetMapping("/page")
    public ApiResult<SysParamInfoQuery> pageByQuery(
            @Parameter(description = "查询条件") // required 默认为 false
            SysParamInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "根据参数标识获取参数值", description = "根据 appId 与参数标识获取对应参数值")
    @GetMapping("/loadParam/{appId}/{paramName}")
    public String loadParam(
            @Parameter(description = "应用/租户 ID", required = true)
            @PathVariable("appId") String appId,
            @Parameter(description = "参数标识", required = true)
            @PathVariable("paramName") String paramName) {
        return service.getParam(paramName,
                Optional.ofNullable(UserUtil.getAppId(request)).orElse(appId));
    }
}