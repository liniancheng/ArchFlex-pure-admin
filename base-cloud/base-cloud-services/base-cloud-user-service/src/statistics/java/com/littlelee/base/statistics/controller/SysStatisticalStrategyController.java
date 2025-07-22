package com.littlelee.base.statistics.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.statistics.model.po.SysStatisticalStrategy;
import com.littlelee.base.statistics.model.query.SysStatisticalStrategyQuery;
import com.littlelee.base.statistics.service.SysStatisticalStrategyService;

import io.swagger.v3.oas.annotations.Operation;

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

/**
 * @author littlelee
 * @date 2020-01-15 17:47:49
 */
@RestController
@RequestMapping("/statisticalStrategy")
@Tag(name = "统计策略操作接口", description = "统计策略相关操作")
public class SysStatisticalStrategyController {
    private static final String FUNC_NAME = "统计策略功能";

    @Autowired
    private SysStatisticalStrategyService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加统计策略")
    @Operation(summary = "添加统计策略", description = "新增统计策略信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "统计策略信息", required = true)
            @RequestBody SysStatisticalStrategy statisticalStrategy) {
        return new ApiResult<>(service.save(statisticalStrategy));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改统计策略")
    @Operation(summary = "修改统计策略", description = "更新统计策略信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "统计策略信息", required = true)
            @RequestBody SysStatisticalStrategy statisticalStrategy) {
        return new ApiResult<>(service.updateById(statisticalStrategy));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除统计策略")
    @Operation(summary = "删除统计策略", description = "根据主键删除统计策略")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "统计策略id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询统计策略信息")
    @Operation(summary = "查询统计策略信息", description = "根据主键查询单条统计策略信息")
    @GetMapping("/{id}")
    public ApiResult<SysStatisticalStrategy> getById(
            @Parameter(description = "统计策略id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "统计策略信息分页查询", description = "分页条件查询统计策略列表")
    @GetMapping("/page")
    public ApiResult<SysStatisticalStrategyQuery> pageByQuery(
            @Parameter(description = "查询条件", required = false)
            SysStatisticalStrategyQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }
}
