package com.littlelee.base.detect.controller;

import com.alibaba.fastjson.JSONObject;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.mapper.VideoRecordsMapper;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.po.VideoRecords;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/video")
@Tag(name = "视频检测Controller", description = "视频检测操作接口")
public class VideoController {

    private static final String FUNC_NAME = "视频检测功能";

    @Autowired
    private VideoRecordsMapper videoRecordsMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "视频检测", description = "使用指定模型和权重文件检测上传的视频", method = "POST")
    @PostMapping("/predict")
    public ApiResult<?> predict(@RequestBody PredictRequest request) {
        if (request == null || request.getInputVideo() == null || request.getInputVideo().isEmpty()) {
            return ApiResult.failed("未提供视频链接");
        } else if (request.getWeight() == null || request.getWeight().isEmpty()) {
            return ApiResult.failed("未提供权重");
        }

        try {
            // 创建请求体
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PredictRequest> requestEntity = new HttpEntity<>(request, headers);

            // 调用 Flask API
            String response = restTemplate.postForObject(DetectConfig.getFlaskUrl() + "predictVideo", requestEntity, String.class);
//            System.out.println("Received response: " + response);
            JSONObject responses = JSONObject.parseObject(response);
            if(responses.get("status").equals(400)){
                return ApiResult.failed("Error: " + responses.get("message"));
            }else {
                VideoRecords videoRecords = new VideoRecords();
                videoRecords.setWeight(request.getWeight());
                videoRecords.setConf(request.getConf());
                videoRecords.setKind(request.getKind());
                videoRecords.setInputVideo(request.getInputImg());
                videoRecords.setUsername(request.getUsername());
                videoRecords.setOutVideo(String.valueOf(responses.get("outVideo")));
                videoRecordsMapper.insert(videoRecords); // 插入到数据库
                return ApiResult.success(response);
            }
        } catch (Exception e) {
            return ApiResult.failed("Error: " + e.getMessage());
        }
    }
}