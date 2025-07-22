package com.littlelee.base.detect.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.model.po.DetectImageRecord;
import com.littlelee.base.detect.model.query.ImageRecordQuery;
import com.littlelee.base.detect.service.ImageRecordService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

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
@Tag(name = "图像识别配置Controller", description = "图像识别配置操作接口")
public class ImageRecordController {
    private static final String FUNC_NAME = "图像识别配置功能";

    @Autowired
    private ImageRecordService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加图像识别配置")
    @Operation(summary = "添加图像识别配置", description = "图像识别配置信息", method = "POST")
    @Parameter(name = "imageRecord", description = "图像识别配置信息", required = true, schema = @Schema(implementation = DetectImageRecord.class))
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody DetectImageRecord imageRecord){
        return new ApiResult<>(service.saveImageRecord(imageRecord));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改图像识别配置")
    @Operation(summary = "修改图像识别配置", description = "图像识别配置信息", method = "PUT")
    @Parameter(name = "imageRecord", description = "图像识别配置信息", required = true, schema = @Schema(implementation = DetectImageRecord.class))
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody DetectImageRecord imageRecord){
        return new ApiResult<>(service.updateImageRecord(imageRecord));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除图像识别配置")
    @Operation(summary = "删除图像识别配置", description = "删除图像识别配置信息", method = "DELETE")
    @Parameter(name = "id", description = "图像识别配置id", required = true, schema = @Schema(type = "string"))
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.deleteImageRecord(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询图像识别配置信息")
    @Operation(summary = "查询图像识别配置信息", description = "通过主键查询图像识别配置信息", method = "GET")
    @Parameter(name = "id", description = "图像识别配置id", required = true, schema = @Schema(type = "string"))
    @GetMapping("/{id}")
    public ApiResult<DetectImageRecord> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getImageRecordById(id));
    }

    @Operation(summary = "图像识别配置信息分页查询", description = "图像识别配置信息分页查询", method = "GET")
    @Parameter(name = "query", description = "图像识别配置信息查询类", required = false, schema = @Schema(implementation = ImageRecordQuery.class))
    @GetMapping("/page")
    public ApiResult<ImageRecordQuery> pageByQuery(ImageRecordQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }
}