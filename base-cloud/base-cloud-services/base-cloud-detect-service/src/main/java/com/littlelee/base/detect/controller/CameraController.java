package com.littlelee.base.detect.controller;

import com.alibaba.fastjson.JSONObject;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.mapper.CameraRecordsMapper;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.po.CameraRecords;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/camera")
@Tag(name = "摄像检测Controller", description = "摄像检测操作接口")
public class CameraController {

    private static final String FUNC_NAME = "摄像检测功能";

    @Autowired
    private CameraRecordsMapper cameraRecordsMapper;

    private final RestTemplate restTemplate = new RestTemplate();

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "图像检测")
    @Operation(summary = "摄像检测", description = "使用指定模型和权重文件进行摄像检测", method = "POST")
    @Parameter(name = "request", description = "检测请求体", required = true)
    @PostMapping("/predict")
    public ApiResult<?> predict(@RequestBody PredictRequest request) {
        if (request.getWeight() == null || request.getWeight().isEmpty()) {
            return ApiResult.failed("未提供权重");
        }

        try {
            // 创建请求体
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<PredictRequest> requestEntity = new HttpEntity<>(request, headers);

            // 调用 Flask API
            String response = restTemplate.postForObject(DetectConfig.getFlaskUrl() + "predictCamera", requestEntity, String.class);
            System.out.println("Received response: " + response);
            JSONObject responses = JSONObject.parseObject(response);
            if(responses.get("status").equals(400)){
                return ApiResult.failed("Error: " + responses.get("message"));
            }else {
                CameraRecords cameraRecords = new CameraRecords();
                cameraRecords.setWeight(request.getWeight());
                cameraRecords.setKind(request.getKind());
                cameraRecords.setUsername(request.getUsername());
                cameraRecords.setOutVideo(String.valueOf(responses.get("outVideo")));
                cameraRecordsMapper.insert(cameraRecords); // 插入到数据库
                return ApiResult.success(response);
            }
        } catch (Exception e) {
            return ApiResult.failed("Error: " + e.getMessage());
        }
    }

    @GetMapping("/file_names")
    public ApiResult<?> getFileNames() {
        try {
            // 调用 Flask API
            String response = restTemplate.getForObject(DetectConfig.getFlaskUrl() + "file_names", String.class);
            return ApiResult.success(response);
        } catch (Exception e) {
            return ApiResult.failed("Error: " + e.getMessage());
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "停止录制")
    @Operation(summary = "停止录制", description = "停止摄像头检测", method = "GET")
    @GetMapping("/stopCamera")
    public ApiResult<?> stopCamera() {
        try {
            // 调用 Flask API
            String response = restTemplate.getForObject(DetectConfig.getFlaskUrl() + "stopCamera", String.class);
            JSONObject responses = JSONObject.parseObject(response);
            // 获取python端返回的信息
            String message =  (String) responses.get("message");
            return ApiResult.success(message);
        } catch (Exception e) {
            return ApiResult.failed("Error: " + e.getMessage());
        }
    }



}
