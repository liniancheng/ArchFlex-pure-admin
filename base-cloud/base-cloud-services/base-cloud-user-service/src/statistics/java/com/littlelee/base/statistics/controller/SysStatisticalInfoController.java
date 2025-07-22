package com.littlelee.base.statistics.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.bo.StatisticalInfo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.statistics.model.po.SysStatisticalInfo;
import com.littlelee.base.statistics.model.query.SysStatisticalInfoQuery;
import com.littlelee.base.statistics.service.SysStatisticalInfoService;

import io.swagger.v3.oas.annotations.Operation;

import java.util.concurrent.Future;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
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
 * @date 2020-01-17 16:47:20
 */
@EnableAsync
@RestController
@RequestMapping("/statisticalInfo")
@Tag(name = "统计信息操作接口", description = "统计信息相关操作")
public class SysStatisticalInfoController {
    private static final String FUNC_NAME = "统计信息功能";

    @Autowired
    private SysStatisticalInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加统计信息")
    @Operation(summary = "添加统计信息", description = "新增统计信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "统计信息信息", required = true)
            @RequestBody SysStatisticalInfo statisticalInfo) {
        return new ApiResult<>(service.save(statisticalInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改统计信息")
    @Operation(summary = "修改统计信息", description = "更新统计信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "统计信息信息", required = true)
            @RequestBody SysStatisticalInfo statisticalInfo) {
        return new ApiResult<>(service.updateById(statisticalInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除统计信息")
    @Operation(summary = "删除统计信息", description = "根据主键删除统计信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "统计信息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询统计信息信息")
    @Operation(summary = "查询统计信息信息", description = "根据主键查询单条统计信息")
    @GetMapping("/{id}")
    public ApiResult<SysStatisticalInfo> getById(
            @Parameter(description = "统计信息id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "统计信息信息分页查询", description = "分页条件查询统计信息列表")
    @GetMapping("/page")
    public ApiResult<SysStatisticalInfoQuery> pageByQuery(
            @Parameter(description = "查询条件") SysStatisticalInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "异步保存统计信息", description = "异步保存统计信息")
    @PostMapping("/save")
    public Future<Boolean> save(
            @Parameter(description = "统计信息", required = true)
            @RequestBody StatisticalInfo statisticalInfo) {
        SysStatisticalInfo sysStatisticalInfo = new SysStatisticalInfo();
        BeanUtils.copyProperties(statisticalInfo, sysStatisticalInfo);
        return new AsyncResult<>(service.saveStatistical(sysStatisticalInfo));
    }
}