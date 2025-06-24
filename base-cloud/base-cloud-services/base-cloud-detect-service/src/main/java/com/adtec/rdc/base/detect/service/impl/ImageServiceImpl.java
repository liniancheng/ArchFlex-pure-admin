package com.adtec.rdc.base.detect.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.detect.mapper.ImageMapper;
import com.adtec.rdc.base.detect.mapper.ImageRecordMapper;
import com.adtec.rdc.base.detect.model.bo.DetectImage;
import com.adtec.rdc.base.detect.model.po.DetectImageRecord;
import com.adtec.rdc.base.detect.model.query.ImageRecordQuery;
import com.adtec.rdc.base.detect.service.ImageRecordService;
import com.adtec.rdc.base.detect.service.ImageService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 图像识别记录服务实现类
 */
@Service
public class ImageServiceImpl extends BaseServiceImpl<ImageMapper, DetectImage> implements ImageService {

    @Autowired
    private ImageRecordMapper mapper;
    private static final String UPLOAD_DIR = "D:\\work\\tobacco\\upload\\";  // 注意路径分隔符使用双反斜杠
    @Autowired
    private ImageRecordService imageRecordService;  // 注入 ImageRecordService

    @Override
    public ImageRecordQuery pageByQuery(ImageRecordQuery query) {
        // 设置排序
        query.setAsc("id");
        // 执行分页查询
        mapper.pageByQuery(query);
        List<DetectImageRecord> records = query.getRecords();
        if (CollectionUtils.isNotEmpty(records)) {
            // 这里可以添加额外的业务逻辑，例如设置额外的字段信息
        }
        return query;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveImage(DetectImage image) {
        // 保存图像识别记录
        return this.save(image);
    }

    @Override
    public Boolean deleteImageRecord(String id) {
        // 根据ID删除图像识别记录
        return this.removeById(id);
    }

    @Override
    public DetectImage getImageById(String id) {
        // 根据ID获取图像识别记录
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateImageRecord(DetectImage detectImage) {
        // 更新图像识别记录
        return this.updateById(detectImage);
    }

    @Override
    public Map<String, Object> detectImage(DetectImage detectImage) {
        String modelName = detectImage.getModel();
        String weightsName = detectImage.getRecognitionWeight();
        String fileName = detectImage.getOriginalImage();

        // 检查参数合法性
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        // 上传目录
        String uploadDir = UPLOAD_DIR;
        String inputImagePath = uploadDir + detectImage.getOriginalImage();
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
        String weightsPath = "D:\\work\\tobacco\\weights\\" + weightsName;
        File weightFile = new File(weightsPath);
        if (!weightFile.exists()) {
            return null;
        }

        // 输出图像路径
        String outputDir = "D:\\work\\tobacco\\result\\";
        File outputPath = new File(outputDir);
        if (!outputPath.exists()) {
            outputPath.mkdirs();
        }

        // 调用 Python 脚本
        ProcessBuilder pb = new ProcessBuilder(
                pythonPath,
                "D:\\PycharmProjects\\ultralytics-main\\ultralytics\\predict.py",
                "--weights", weightsPath,
                "--image", inputImagePath,
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

}