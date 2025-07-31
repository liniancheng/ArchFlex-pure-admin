package com.littlelee.base.detect.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.littlelee.base.common.util.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/camera")
@Tag(name = "摄像检测Controller", description = "摄像检测操作接口")
public class CameraController {


    @PostMapping("/detect")
    public ApiResult<Map<String, Object>> detectFrame(@RequestParam("frame") MultipartFile frame) throws IOException, InterruptedException {
        File tempFile = File.createTempFile("frame_", ".jpg");
        frame.transferTo(tempFile);

        String weightsPath = "D:/work/tobacco/weights/yolov8n.pt";

        ProcessBuilder pb = new ProcessBuilder(
                "D:/work/miniconda3/envs/yolov11/python.exe",
                "D:/PycharmProjects/ultralytics-main/ultralytics/detect_frame.py",
                "--weights", weightsPath,
                "--input", tempFile.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line);
        }
        process.waitFor();

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = mapper.readValue(output.toString(), Map.class);

        return ApiResult.success(result);
    }



}
