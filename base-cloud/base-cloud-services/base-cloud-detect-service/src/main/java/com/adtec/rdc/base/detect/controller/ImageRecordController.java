package com.adtec.rdc.base.detect.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.detect.model.po.DetectImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.adtec.rdc.base.detect.service.ImageRecordService;

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
 * @author littlelee
 * @date 2025-06-17 10:00:00
 */
@RestController
@RequestMapping("/imageRecord")
@Api(value = "图像识别配置controller", tags = {"图像识别配置操作接口"})
public class ImageRecordController {
    private static final String FUNC_NAME = "图像识别配置功能";

    @Autowired
    private ImageRecordService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加图像识别配置")
    @ApiOperation(value = "添加图像识别配置", notes = "图像识别配置信息", httpMethod = "POST")
    @ApiImplicitParam(name = "imageRecord", value = "图像识别配置信息", required = true, dataType = "ImageRecord")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody DetectImageRecord imageRecord){
        return new ApiResult<>(service.saveImageRecord(imageRecord));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改图像识别配置")
    @ApiOperation(value = "修改图像识别配置", notes = "图像识别配置信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "imageRecord", value = "图像识别配置信息", required = true, dataType = "ImageRecord")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody DetectImageRecord imageRecord){
        return new ApiResult<>(service.updateImageRecord(imageRecord));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除图像识别配置")
    @ApiOperation(value = "删除图像识别配置", notes = "删除图像识别配置信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "图像识别配置id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.deleteImageRecord(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询图像识别配置信息")
    @ApiOperation(value = "查询图像识别配置信息", notes = "通过主键查询图像识别配置信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "图像识别配置id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<DetectImageRecord> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getImageRecordById(id));
    }

    @ApiOperation(value = "图像识别配置信息分页查询", notes = "图像识别配置信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "图像识别配置信息查询类", required = false, dataType = "ImageRecordQuery")
    @GetMapping("/page")
    public ApiResult<ImageRecordQuery> pageByQuery(ImageRecordQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
}