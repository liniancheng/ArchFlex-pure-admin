package com.adtec.rdc.base.statistics.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.bo.StatisticalInfo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalInfo;
import com.adtec.rdc.base.statistics.model.query.SysStatisticalInfoQuery;
import com.adtec.rdc.base.statistics.service.SysStatisticalInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.concurrent.Future;

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
 * @author hewei
 * @date 2020-01-17 16:47:20
 */
@EnableAsync
@RestController
@RequestMapping("/statisticalInfo")
@Api(value = "统计信息controller", tags = {"统计信息操作接口"})
public class SysStatisticalInfoController {
	private static final String FUNC_NAME = "统计信息功能";

    @Autowired
    private SysStatisticalInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加统计信息")
    @ApiOperation(value = "添加统计信息", notes = "统计信息信息", httpMethod = "POST")
    @ApiImplicitParam(name = "statisticalInfo", value = "统计信息信息", required = true, dataType = "SysStatisticalInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysStatisticalInfo statisticalInfo){
        return new ApiResult<>(service.save(statisticalInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改统计信息")
    @ApiOperation(value = "修改统计信息", notes = "统计信息信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "statisticalInfo", value = "统计信息信息", required = true, dataType = "SysStatisticalInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysStatisticalInfo statisticalInfo){
        return new ApiResult<>(service.updateById(statisticalInfo));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除统计信息")
    @ApiOperation(value = "删除统计信息", notes = "删除统计信息信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "统计信息id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询统计信息信息")
    @ApiOperation(value = "查询统计信息信息", notes = "通过主键查询统计信息信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "统计信息id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysStatisticalInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "统计信息信息分页查询", notes = "统计信息信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "统计信息信息查询类", required = false, dataType = "SysStatisticalInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysStatisticalInfoQuery> pageByQuery(SysStatisticalInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    @PostMapping("/save")
    public Future<Boolean> save(@RequestBody StatisticalInfo statisticalInfo){
    	SysStatisticalInfo sysStatisticalInfo = new SysStatisticalInfo();
        BeanUtils.copyProperties(statisticalInfo, sysStatisticalInfo);
        return new AsyncResult<>(service.saveStatistical(sysStatisticalInfo));
    }
}
