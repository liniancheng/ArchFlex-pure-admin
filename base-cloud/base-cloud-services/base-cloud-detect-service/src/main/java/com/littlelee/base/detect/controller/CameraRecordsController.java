package com.littlelee.base.detect.controller;

import com.alibaba.fastjson.JSONObject;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.mapper.CameraRecordsMapper;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.po.CameraRecords;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author littlelee
 * @date 2025-08-01 10:00:00
 */
@RestController
@RequestMapping("/cameraRecords")
@Tag(name = "摄像记录Controller", description = "摄像记录操作接口")
public class CameraRecordsController {

    @Autowired
    private CameraRecordsMapper cameraRecordsMapper;



    @PostMapping
    public ApiResult<?> save(@RequestBody CameraRecords cameraRecords) {
        System.out.println(cameraRecords);
        cameraRecordsMapper.insert(cameraRecords);
        return ApiResult.success("检测记录保存成功");
    }

}
