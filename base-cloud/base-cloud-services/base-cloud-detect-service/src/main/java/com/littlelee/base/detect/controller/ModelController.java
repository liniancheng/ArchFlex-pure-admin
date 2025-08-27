package com.littlelee.base.detect.controller;

import com.alibaba.fastjson.JSONObject;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/model")
public class ModelController {

    private static final String FUNC_NAME = "模型管理功能";

    private final RestTemplate restTemplate = new RestTemplate();

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "获取权重文件")
    @Operation(summary = "模型管理", description = "获取所有模型权重文件", method = "GET")
    @GetMapping("/weightNames")
    public ApiResult<?> getFileNames() {
        try {
            // 调用 Flask API
            String response = restTemplate.getForObject(DetectConfig.getFlaskUrl() + "file_names", String.class);
            JSONObject responses = JSONObject.parseObject(response);
            return ApiResult.success(responses);
        } catch (Exception e) {
            return ApiResult.failed("Error: " + e.getMessage());
        }
    }


}
