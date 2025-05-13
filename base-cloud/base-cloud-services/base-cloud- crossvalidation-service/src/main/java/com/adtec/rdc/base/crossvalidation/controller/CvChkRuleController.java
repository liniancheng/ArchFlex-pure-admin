package com.adtec.rdc.base.crossvalidation.controller;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.crossvalidation.model.query.CvRuleQuery;
import com.adtec.rdc.base.crossvalidation.service.CvChkIndRuleChkService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cvrule")
public class CvChkRuleController {

    @Autowired
    private CvChkIndRuleChkService service;

    @ApiOperation(value = "指标规则分页查询", notes = "指标规则分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "指标规则分页查询", required = false, dataType = "CvRuleQuery")
    @PostMapping("/page")
    public ApiResult<CvRuleQuery> pageByQuery(@RequestBody CvRuleQuery query){
        return new ApiResult<>(service.rulePageByQuery(query));
    }
}
