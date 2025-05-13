package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndOrgRule;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndOrgRuleQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndOrgRuleService;

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

import javax.servlet.http.HttpServletRequest;

/**
 * @author adtec
 * @date 2022-03-07 19:56:16
 */
@RestController
@RequestMapping("/cvindorg")
@Api(value = "指标法人配置controller", tags = {"指标法人配置操作接口"})
public class CvChkIndOrgRuleController {
	private static final String FUNC_NAME = "指标法人配置功能";

    @Autowired
    private CvChkIndOrgRuleService service;

    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加指标法人配置")
    @ApiOperation(value = "添加指标法人配置", notes = "指标法人配置信息", httpMethod = "POST")
    @ApiImplicitParam(name = "cvindorg", value = "指标法人配置信息", required = true, dataType = "CvChkIndOrgRule")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody CvChkIndOrgRule cvindorg){
        return new ApiResult<>(service.saveCvChkIndOrgRule(cvindorg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改指标法人配置")
    @ApiOperation(value = "修改指标法人配置", notes = "指标法人配置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "cvindorg", value = "指标法人配置信息", required = true, dataType = "CvChkIndOrgRule")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody CvChkIndOrgRule cvindorg){
        return new ApiResult<>(service.updateById(cvindorg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除指标法人配置")
    @ApiOperation(value = "删除指标法人配置", notes = "删除指标法人配置信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "指标法人配置id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.delete(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询指标法人配置信息")
    @ApiOperation(value = "查询指标法人配置信息", notes = "通过主键查询指标法人配置信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "指标法人配置id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<CvChkIndOrgRule> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "指标法人配置信息分页查询", notes = "指标法人配置信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "指标法人配置信息查询类", required = false, dataType = "CvChkIndOrgRuleQuery")
    @GetMapping("/page")
    public ApiResult<CvChkIndOrgRuleQuery> pageByQuery(CvChkIndOrgRuleQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
}
