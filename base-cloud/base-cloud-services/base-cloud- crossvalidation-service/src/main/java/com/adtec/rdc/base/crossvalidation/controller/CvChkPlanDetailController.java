package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.crossvalidation.model.bo.CvChkPlanDetailBo;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkPlanDetail;
import com.adtec.rdc.base.crossvalidation.model.query.CvChkPlanDetailQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkPlanDetailService;

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
 * @date 2022-03-07 20:02:56
 */
@RestController
@RequestMapping("/cvplandetail")
@Api(value = "指标检核计划明细controller", tags = {"指标检核计划明细操作接口"})
public class CvChkPlanDetailController {
	private static final String FUNC_NAME = "指标检核计划明细功能";

    @Autowired
    private CvChkPlanDetailService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "指标检核计划明细创建")
    @ApiOperation(value = "指标检核计划明细创建", notes = "指标检核计划明细信息", httpMethod = "POST")
    @ApiImplicitParam(name = "cvplandetail", value = "指标检核计划明细信息", required = true, dataType = "CvChkPlanDetail")
    @PostMapping("/execSql")
    public ApiResult<Boolean> execSql(@RequestBody CvChkPlanDetailBo cvChkPlanDetailBo){
        return new ApiResult<>(service.execSql(cvChkPlanDetailBo));
    }
}
