package com.adtec.rdc.base.statistics.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalStrategy;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalStrategyQuery;
import com.adtec.rdc.base.statistics.service.SysStatisticalStrategyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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
 * @author hewei
 * @date 2020-01-15 17:47:49
 */
@RestController
@RequestMapping("/statisticalStrategy")
@Api(value = "统计策略controller", tags = {"统计策略操作接口"})
public class SysStatisticalStrategyController {
	private static final String FUNC_NAME = "统计策略功能";

    @Autowired
    private SysStatisticalStrategyService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加统计策略")
    @ApiOperation(value = "添加统计策略", notes = "统计策略信息", httpMethod = "POST")
    @ApiImplicitParam(name = "statisticalStrategy", value = "统计策略信息", required = true, dataType = "SysStatisticalStrategy")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysStatisticalStrategy statisticalStrategy){
        return new ApiResult<>(service.save(statisticalStrategy));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改统计策略")
    @ApiOperation(value = "修改统计策略", notes = "统计策略信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "statisticalStrategy", value = "统计策略信息", required = true, dataType = "SysStatisticalStrategy")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysStatisticalStrategy statisticalStrategy){
        return new ApiResult<>(service.updateById(statisticalStrategy));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除统计策略")
    @ApiOperation(value = "删除统计策略", notes = "删除统计策略信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "统计策略id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询统计策略信息")
    @ApiOperation(value = "查询统计策略信息", notes = "通过主键查询统计策略信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "统计策略id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysStatisticalStrategy> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "统计策略信息分页查询", notes = "统计策略信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "统计策略信息查询类", required = false, dataType = "SysStatisticalStrategyQuery")
    @GetMapping("/page")
    public ApiResult<SysStatisticalStrategyQuery> pageByQuery(SysStatisticalStrategyQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
