package com.adtec.rdc.base.entrancevalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.entrancevalidation.model.po.ChkVerifyConfig;
import com.adtec.rdc.base.entrancevalidation.model.query.ChkVerifyConfigQuery;
import com.adtec.rdc.base.entrancevalidation.service.ChkVerifyConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ChkVerifyConfigController
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 11:02:47
 **/
@RestController
@RequestMapping("/entranceind")
@Api(value = "校验规则配置controller", tags = {"校验规则配置操作接口"})
public class ChkVerifyConfigController {

    private static final String FUNC_NAME = "入口校验规则设置功能";

    @Autowired
    private ChkVerifyConfigService chkVerifyConfigService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加指标规则校验设置")
    @ApiOperation(value = "添加指标规则校验设置", notes = "指标规则校验设置信息", httpMethod = "POST")
    @ApiImplicitParam(name = "cvindrule", value = "指标规则校验设置信息", required = true, dataType = "ChkVerifyConfig")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody ChkVerifyConfig chkVerifyConfig) {

        return new ApiResult<>(chkVerifyConfigService.saveChkVerifyConfig(chkVerifyConfig));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改指标规则校验设置")
    @ApiOperation(value = "修改指标规则校验设置", notes = "指标规则校验设置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "cvindrule", value = "指标规则校验设置信息", required = true, dataType = "ChkVerifyConfig")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody ChkVerifyConfig chkVerifyConfig) {

        return new ApiResult<>(chkVerifyConfigService.updateChkVerifyConfig(chkVerifyConfig));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改指标规则校验设置")
    @ApiOperation(value = "修改指标规则校验设置", notes = "指标规则校验设置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "cvindrule", value = "指标规则校验设置信息", required = true, dataType = "string")
    @PostMapping
    public ApiResult<Boolean> updateVldFlg(@RequestBody List<String> ruleNos, String vldFlg){
        return new ApiResult<>(chkVerifyConfigService.updateVldFlg(ruleNos, vldFlg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询指标规则校验设置信息")
    @ApiOperation(value = "查询指标规则校验设置信息", notes = "通过主键查询指标规则校验设置信息", httpMethod = "GET")
    @ApiImplicitParam(name = "ruleNo", value = "指标规则校验设置id", required = true, dataType = "string")
    @GetMapping("/{ruleNo}")
    public ApiResult<ChkVerifyConfig> getById(@PathVariable("ruleNo") String ruleNo) {
        ChkVerifyConfig chkVerifyConfig = chkVerifyConfigService.getById(ruleNo);
        return new ApiResult<>(chkVerifyConfig);
    }

    @ApiOperation(value = "校验配置信息分页查询", notes = "校验配置信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "校验配置信息查询类", required = false, dataType = "ChkVerifyConfigQuery")
    @GetMapping("/page")
    public ApiResult<ChkVerifyConfigQuery> pageByQuery(ChkVerifyConfigQuery query) {
        return new ApiResult<>(chkVerifyConfigService.pageByQuery(query));
    }

    @ApiOperation(value = "获取系统名", notes = "获取系统名", httpMethod = "GET")
    @GetMapping("/sysNmList")
    public ApiResult<List<String>> getSysNmList(){
        return new ApiResult<>(chkVerifyConfigService.getSysNmList());
    }

    @ApiOperation(value = "获取表名", notes = "获取表名", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "系统名", required = false, dataType = "String")
    @GetMapping("/sysTabNmList/{sysNm}")
    public ApiResult<List<String>> getSysTabNm(@PathVariable("sysNm") String sysNm){
        return new ApiResult<>(chkVerifyConfigService.getSysTabNmList(sysNm));
    }

    @ApiOperation(value = "获取字段名", notes = "获取表名", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "系统名", required = false, dataType = "String")
    @GetMapping("/sysColumnList/{sysTabNm}")
    public ApiResult<List<String>> getColumnNm(@PathVariable("sysTabNm") String sysTabNm){
        return new ApiResult<>(chkVerifyConfigService.getColumnNm(sysTabNm));
    }

}
