package com.littlelee.base.detect.websocket;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

public class PythonDetectService {

    private static final String PYTHON_PATH = "D:/work/miniconda3/envs/yolov11/python.exe";
    private static final String DETECT_SCRIPT = "D:/PycharmProjects/ultralytics-main/ultralytics/detect_frame.py";

    public String detectFromBase64(String base64) throws IOException, InterruptedException {
        // 1. 将Base64解码为图像
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        Files.createDirectories(Path.of("temp"));  // <--- 新增，确保 temp 目录存在
        String tempInputPath = "temp/" + UUID.randomUUID() + ".jpg";
        String tempOutputPath = "temp/" + UUID.randomUUID() + "_out.jpg";
        Files.write(Path.of(tempInputPath), imageBytes);

        String weightsPath = "D:/work/tobacco/weights/yolov8n.pt";

        // 2. 调用Python脚本检测
        ProcessBuilder pb = new ProcessBuilder(
                PYTHON_PATH, DETECT_SCRIPT,
                "--weights", weightsPath,
                "--input", tempInputPath,
                "--output", tempOutputPath
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // 输出日志（可选）
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[PYTHON] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Python脚本执行失败");
        }

        // 3. 将处理后的图像再编码为Base64
        byte[] processedBytes = Files.readAllBytes(Path.of(tempOutputPath));
        return Base64.getEncoder().encodeToString(processedBytes);
    }
}
