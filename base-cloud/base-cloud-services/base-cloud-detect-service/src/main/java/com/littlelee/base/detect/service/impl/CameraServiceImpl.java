package com.littlelee.base.detect.service.impl;

import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.mapper.CameraMapper;
import com.littlelee.base.detect.model.bo.DetectCamera;
import com.littlelee.base.detect.service.CameraService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class CameraServiceImpl extends BaseServiceImpl<CameraMapper, DetectCamera> implements CameraService {
    @Override
    public Map<String, Object> detectCamera(DetectCamera detectCamera) {

        String modelName = detectCamera.getModel();
        String weightsName = detectCamera.getRecognitionWeight();

        // 摄像检测标识：camera
        String input = detectCamera.getInput();


        // 根据 modelName 决定使用哪个 conda 环境的 python 路径
        String pythonPath;
        switch (modelName.toLowerCase()) {
            case "yolo":
                pythonPath = "D:/work/miniconda3/envs/yolov11/python.exe";
                break;
            case "rtdetr":
                pythonPath = "D:\\miniconda3\\envs\\rtdetr\\python.exe";
                break;
            default:
                return null;
        }

        // 权重文件路径（假设你统一放在一个文件夹下）
        String weightsPath = DetectConfig.getWeightsPath() + "/" + weightsName;
        File weightFile = new File(weightsPath);
        if (!weightFile.exists()) {
            return null;
        }

        // 输出图像路径
        String outputDir = DetectConfig.getOutputDir();
        File outputPath = new File(outputDir);
        if (!outputPath.exists()) {
            outputPath.mkdirs();
        }

        // 调用 Python 脚本
        ProcessBuilder pb = new ProcessBuilder(
                pythonPath,
                DetectConfig.getScriptPath(),
                "--weights", weightsPath,
                "--input", input,
                "--output", outputDir
        );
        pb.redirectErrorStream(true); // 合并标准输出和错误输出

        try {
            Process process = pb.start();

            // 读取 Python 输出
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return null;
            }


            // 构造返回结果
            Map<String, Object> resultData = new HashMap<>();

            return resultData;

        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}
