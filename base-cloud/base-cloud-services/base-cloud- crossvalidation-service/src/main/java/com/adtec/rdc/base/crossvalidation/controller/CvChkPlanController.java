package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlan;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanService;

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
import java.util.Date;
import java.util.Map;

/**
 * @author adtec
 * @date
 */
@RestController
@RequestMapping("/cvchkplan")
@Api(value = "指标检核计划controller", tags = {"指标检核计划操作接口"})
public class CvChkPlanController {
	private static final String FUNC_NAME = "指标检核计划功能";

    @Autowired
    private CvChkPlanService service;

    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加指标检核计划")
    @ApiOperation(value = "添加指标检核计划", notes = "指标检核计划信息", httpMethod = "POST")
    @ApiImplicitParam(name = "cvchkplan", value = "指标检核计划信息", required = true, dataType = "CvChkPlan")
    @PostMapping
    public ApiResult<Map<String,String>> save(@RequestBody CvChkPlan cvchkplan){
        cvchkplan.setCreateUser(UserUtil.getLoginName(request));
        cvchkplan.setCreateTime(DateUtil.format(new Date()));
        return new ApiResult<>(service.saveCvChkPlan(cvchkplan));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除指标检核计划")
    @ApiOperation(value = "删除指标检核计划", notes = "删除指标检核计划信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "指标检核计划id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.deletePlan(id));
    }


    @ApiOperation(value = "指标检核计划信息查询", notes = "指标检核计划信息查询", httpMethod = "POST")
    @ApiImplicitParam(name = "query", value = "指标检核计划信息查询类", required = false, dataType = "CvChkPlanQuery")
    @PostMapping("/page")
    public ApiResult<CvChkPlanQuery> showPage(@RequestBody CvChkPlanQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
}
