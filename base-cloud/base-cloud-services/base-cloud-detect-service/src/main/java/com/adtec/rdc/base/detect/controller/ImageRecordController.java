package com.adtec.rdc.base.detect.controller;

import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.adtec.rdc.base.detect.service.ImageRecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imageRecord")
public class ImageRecordController {

    @Autowired
    private ImageRecordService service;

    @ApiOperation(value = "图片识别记录分页查询", notes = "图片识别记录分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "图片识别记录分页查询", required = false, dataType = "ImageRecordQuery")
    @PostMapping("/page")
    public ApiResult<ImageRecordQuery> pageByQuery(@RequestBody ImageRecordQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
}
