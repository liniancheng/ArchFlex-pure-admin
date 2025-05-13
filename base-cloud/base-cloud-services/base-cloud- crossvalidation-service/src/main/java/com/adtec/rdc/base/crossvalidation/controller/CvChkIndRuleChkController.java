package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.base.service.feign.BaseUserService;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkIndRuleChk;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkIndRuleChkQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndRuleChkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
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
import java.util.Date;

/**
 * @author adtec
 * @date 2022-03-07 19:51:20
 */
@RestController
@RequestMapping("/cvindrule")
@Api(value = "指标规则校验设置controller", tags = {"指标规则校验设置操作接口"})
public class CvChkIndRuleChkController {
    private static final String FUNC_NAME = "指标规则校验设置功能";

    @Autowired
    private CvChkIndRuleChkService service;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private BaseUserService sysBranchInfoService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加指标规则校验设置")
    @ApiOperation(value = "添加指标规则校验设置", notes = "指标规则校验设置信息", httpMethod = "POST")
    @ApiImplicitParam(name = "cvindrule", value = "指标规则校验设置信息", required = true, dataType = "CvChkIndRuleChk")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody CvChkIndRuleChk cvindrule) {
        cvindrule.setCreateUser(UserUtil.getLoginName(request));
        cvindrule.setCreateTime(DateUtil.format(new Date()));
        return new ApiResult<>(service.saveCvChkIndRuleChk(cvindrule));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改指标规则校验设置")
    @ApiOperation(value = "修改指标规则校验设置", notes = "指标规则校验设置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "cvindrule", value = "指标规则校验设置信息", required = true, dataType = "CvChkIndRuleChk")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody CvChkIndRuleChk cvindrule) {
        cvindrule.setUpdateUser(UserUtil.getLoginName(request));
        cvindrule.setUpdateTime(DateUtil.format(new Date()));
        return new ApiResult<>(service.updateCvChkIndRuleChk(cvindrule));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除指标规则校验设置")
    @ApiOperation(value = "删除指标规则校验设置", notes = "删除指标规则校验设置信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "指标规则校验设置id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询指标规则校验设置信息")
    @ApiOperation(value = "查询指标规则校验设置信息", notes = "通过主键查询指标规则校验设置信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "指标规则校验设置id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<CvChkIndRuleChk> getById(@PathVariable("id") String id) {
        CvChkIndRuleChk cvChkIndRuleChk = service.getById(id);
        String orgVal = cvChkIndRuleChk.getOrgVal();
        if (StringUtils.isNotBlank(orgVal)){
            SysBranchInfoVo sysBranchInfo = sysBranchInfoService.selectById(orgVal);
            if (sysBranchInfo != null && StringUtils.isNotBlank(sysBranchInfo.getBranchName())) {
                cvChkIndRuleChk.setOrgNm(sysBranchInfo.getBranchName());
            }
        }
        return new ApiResult<>(cvChkIndRuleChk);
    }

    @ApiOperation(value = "指标规则校验设置信息分页查询", notes = "指标规则校验设置信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "指标规则校验设置信息查询类", required = false, dataType = "CvChkIndRuleChkQuery")
    @GetMapping("/page")
    public ApiResult<CvChkIndRuleChkQuery> pageByQuery(CvChkIndRuleChkQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }
}
