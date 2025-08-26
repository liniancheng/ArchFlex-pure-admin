package com.littlelee.base.detect.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.littlelee.base.common.base.service.impl.BaseServiceImpl;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.mapper.ImageMapper;
import com.littlelee.base.detect.mapper.ImageRecordMapper;
import com.littlelee.base.detect.mapper.ImgRecordsMapper;
import com.littlelee.base.detect.model.bo.DetectImage;
import com.littlelee.base.detect.model.bo.PredictRequest;
import com.littlelee.base.detect.model.bo.PredictResult;
import com.littlelee.base.detect.model.po.DetectImageRecord;
import com.littlelee.base.detect.model.po.ImgRecords;
import com.littlelee.base.detect.model.query.ImageRecordQuery;
import com.littlelee.base.detect.service.ImageRecordService;
import com.littlelee.base.detect.service.ImageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;


/**
 * 图像识别记录服务实现类
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageMapper, DetectImage> implements ImageService {

    @Autowired
    private ImageRecordMapper mapper;
    @Autowired
    private ImageRecordService imageRecordService;  // 注入 ImageRecordService

    private final RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private ImgRecordsMapper imgRecordsMapper;

    @Override
    public Map<String, Object> detectImage(DetectImage detectImage) {
        String modelName = detectImage.getModel();
        String weightsName = detectImage.getRecognitionWeight();
        String fileUrl = detectImage.getOriginalImage();  // http://127.0.0.1:8898/uploads/2025/07/11/1752218067794.jpg
        URI uri = URI.create(fileUrl);
        // 获取 /2025/07/11/1752218067794.jpg
        String path = uri.getPath().substring("/uploads".length());
        String fileName = path.substring(path.lastIndexOf('/') + 1);

        // 原始图片路径
        String inputImagePath = DetectConfig.getProfile() + path;
        File inputFile = new File(inputImagePath);
        if (!inputFile.exists()) {
            return null;
        }

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
                "--input", inputImagePath,
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

            // 提取 JSON 数据
            String outputStr = output.toString();
            String jsonPattern = "\\{.*\\}";
            Pattern pattern = Pattern.compile(jsonPattern);
            Matcher matcher = pattern.matcher(outputStr);

            if (matcher.find()) {
                String jsonResult = matcher.group();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> detectionResults = objectMapper.readValue(jsonResult, Map.class);

                // 推理成功后构造返回结果
                String resultFileName = "result_" + fileName;

                // 创建 ImageRecord 实例并保存
                DetectImageRecord imageRecord = new DetectImageRecord();
                imageRecord.setOriginalImage("http://localhost:8898/uploads/" + fileName);
                imageRecord.setPredictedImage("http://localhost:8898/result/" + resultFileName);
                imageRecord.setRecognitionWeight(weightsName);
                imageRecord.setMinThreshold(detectImage.getMinThreshold()); // 示例值，根据实际情况设置
                imageRecord.setAiAssistant(detectImage.getAiAssistant()); // 示例值，根据实际情况设置
                imageRecord.setAiSuggestion("建议使用更高权重"); // 示例值，根据实际情况设置
                imageRecord.setRecognitionTime(new Date());
                imageRecord.setRecognitionUser("admin"); // 示例值，根据实际情况设置

                boolean saveResult = imageRecordService.saveImageRecord(imageRecord);
                if (!saveResult) {
                    return null;
                }

                // 构造返回结果
                Map<String, Object> resultData = new HashMap<>();
                resultData.put("resultFileName", "http://localhost:8898/result/" + resultFileName);
                resultData.put("detectionResults", detectionResults);

                return resultData;
            } else {
                return null;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    @Override
    public PredictResult predict(PredictRequest request) {
        // 创建请求体
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PredictRequest> requestEntity = new HttpEntity<>(request, headers);
        String response = restTemplate.postForObject(DetectConfig.getFlaskUrl() + "predictImg", requestEntity, String.class);
        JSONObject responses = JSONObject.parseObject(response);
        String message = String.valueOf(responses.get("message"));
        String status = String.valueOf(responses.get("status"));
        PredictResult predictResult = new PredictResult();
        if (status.equals(400)){
            predictResult.setStatus(status);
            predictResult.setMessage(message);
            return predictResult;
        }else {
            // 获取Flask端的返回结果
            String outImg = String.valueOf(responses.get("outImg"));
            String allTime = String.valueOf(responses.get("allTime"));
            String label = String.valueOf(responses.get("label"));
            String confidence = String.valueOf(responses.get("confidence"));
            ImgRecords imgRecords = new ImgRecords();
            imgRecords.setWeight(request.getWeight());
            imgRecords.setConf(request.getConf());
            imgRecords.setKind(request.getKind());
            imgRecords.setInputImg(request.getInputImg());
            imgRecords.setUsername(request.getUsername());
            imgRecords.setStartTime(request.getStartTime());
            imgRecords.setLabel(label);
            imgRecords.setConfidence(confidence);
            imgRecords.setAllTime(allTime);
            imgRecords.setOutImg(outImg);
            imgRecordsMapper.insert(imgRecords); // 插入到数据库
            // 构造返回结果
            predictResult.setOutImg(outImg);
            predictResult.setAllTime(allTime);
            predictResult.setStatus(status);
            predictResult.setMessage(message);
            // 统计每种检测类别的数目
            Map<String, Integer> labelCounts = countLabels(label);
            predictResult.setLabelCounts((HashMap<String, Integer>) labelCounts);
            return predictResult;
        }
    }

    // 统计每种检测类别的数目
    private Map<String, Integer> countLabels(String labelStr) {
        // 去掉首尾的括号并分割字符串为标签列表
        List<String> labels = Arrays.asList(labelStr.substring(1, labelStr.length() - 1).split(", "));
        Map<String, Integer> labelCounts = new HashMap<>();
        for (String label : labels) {
            label = label.trim().replace("\"", ""); // 去掉多余的引号并去除空格
            labelCounts.put(label, labelCounts.getOrDefault(label, 0) + 1);
        }
        return labelCounts;
    }


}