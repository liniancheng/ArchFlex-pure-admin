package com.adtec.rdc.base.entrancevalidation.controller;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.entrancevalidation.model.query.ChkVerifyInfoQuery;
import com.adtec.rdc.base.entrancevalidation.service.ChkVerifyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ChkVerifyInfoController
 * @Description TODO
 * @Author lihongbo
 * @Date 2022-07-08 15:03:49
 **/
@RestController
@RequestMapping("/chkresult")
@Api(value = "校验结果controller", tags = {"校验结果操作接口"})
public class ChkVerifyInfoController {

    @Autowired
    private ChkVerifyInfoService service;

    @ApiOperation(value = "校验结果分页查询", notes = "校验结果分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "校验结果查询类", required = false, dataType = "ChkVerifyInfoQuery")
    @GetMapping("/page")
    public ApiResult<ChkVerifyInfoQuery> pageByQuery(ChkVerifyInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @ApiOperation(value = "获取系统名", notes = "获取系统名", httpMethod = "GET")
    @GetMapping("/sysNmList")
    public ApiResult<List<String>> getSysNmList(){
        return new ApiResult<>(service.getSysNmList());
    }

    @ApiOperation(value = "获取表名", notes = "获取表名", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "系统名", required = false, dataType = "String")
    @GetMapping("/sysTabNmList/{sysNm}")
    public ApiResult<List<String>> getSysTabNm(@PathVariable("sysNm") String sysNm){
        return new ApiResult<>(service.getSysTabNmList(sysNm));
    }

    @ApiOperation(value = "获取法人", notes = "获取法人", httpMethod = "GET")
    @GetMapping("/lgprList")
    public ApiResult<List<String>> getLgprList(){
        return new ApiResult<>(service.getLgprList());
    }

}
