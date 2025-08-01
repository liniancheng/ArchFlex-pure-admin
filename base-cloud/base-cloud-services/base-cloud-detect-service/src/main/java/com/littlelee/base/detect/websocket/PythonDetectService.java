package com.littlelee.base.detect.websocket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

public class PythonDetectService {

    private static final String PYTHON_PATH = "D:/work/miniconda3/envs/yolov11/python.exe";
    private static final String DETECT_SCRIPT = "D:/PycharmProjects/ultralytics-main/ultralytics/detect_frame_api.py";

    public String detectFromBase64(String base64) throws IOException, InterruptedException {


        String weightsPath = "D:/work/tobacco/weights/yolov8n.pt";

        // 2. 调用Python脚本检测
        ProcessBuilder pb = new ProcessBuilder(
                PYTHON_PATH, DETECT_SCRIPT
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 将Base64图像写入到标准输入
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write(base64);
            writer.flush();
        }

        // 读取Python返回的检测图像Base64
        StringBuilder outputBase64 = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                outputBase64.append(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python脚本执行失败");
        }

        return outputBase64.toString();
    }
}
