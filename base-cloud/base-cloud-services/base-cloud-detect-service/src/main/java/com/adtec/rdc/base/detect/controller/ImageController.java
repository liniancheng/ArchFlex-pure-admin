package com.adtec.rdc.base.detect.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.detect.model.po.DetectImageRecord;
import com.adtec.rdc.base.detect.service.ImageRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/image")
@Api(value = "图像检测Controller", tags = {"图像检测操作接口"})
public class ImageController {

    private static final String FUNC_NAME = "图片检测功能";

    private static final String UPLOAD_DIR = "D:\\work\\tobacco\\upload\\";  // 注意路径分隔符使用双反斜杠

    @Autowired
    private ImageRecordService imageRecordService;  // 注入 ImageRecordService

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "上传文件")
    @ApiOperation(value = "上传文件", notes = "上传文件到服务器", httpMethod = "POST")
    @ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "MultipartFile")
    @PostMapping("/upload")
    public ApiResult<String> uploadFile(@RequestParam("file") MultipartFile file) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ApiResult.failed("文件为空，请选择一个文件");
        }

        // 获取文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ApiResult.failed("文件名称为空");
        }

        // 设置保存文件的路径
        String uploadDir = UPLOAD_DIR;
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 如果目录不存在，创建目录
        }

        // 保存文件到指定路径
        try {
            file.transferTo(new File(uploadDir + fileName));
            // 构造成功响应对象并设置文件名作为 data
            ApiResult<String> result = ApiResult.success("文件上传成功");
            result.setData("http://localhost:8898/uploads/" + fileName);  // 只返回文件名
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.failed("文件上传失败：" + e.getMessage());
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "图像检测")
    @ApiOperation(value = "图像检测", notes = "使用指定模型和权重文件检测上传的图像", httpMethod = "POST")
    @PostMapping("/detect")
    public ApiResult<String> detectImage(@RequestParam("file") String fileName,
                                         @RequestParam("model") String modelName,
                                         @RequestParam("weights") String weightsName) {
        // 检查参数合法性
        if (fileName == null || fileName.trim().isEmpty()) {
            return ApiResult.failed("文件名为空，请提供已上传的文件名");
        }

        // 上传目录
        String uploadDir = UPLOAD_DIR;
        String inputImagePath = uploadDir + fileName;
        File inputFile = new File(inputImagePath);
        if (!inputFile.exists()) {
            return ApiResult.failed("指定的图像文件不存在：" + inputImagePath);
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
                return ApiResult.failed("不支持的模型类型：" + modelName);
        }

        // 权重文件路径（假设你统一放在一个文件夹下）
        String weightsPath = "D:\\work\\tobacco\\weights\\" + weightsName;
        File weightFile = new File(weightsPath);
        if (!weightFile.exists()) {
            return ApiResult.failed("指定权重文件不存在：" + weightsName);
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

            // 可选：打印 Python 输出
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Python] " + line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return ApiResult.failed("模型推理失败，退出码：" + exitCode);
            }
            // 推理成功后构造返回结果
            String resultFileName = "result_" + fileName;

            // 创建 ImageRecord 实例并保存
            DetectImageRecord imageRecord = new DetectImageRecord();
            imageRecord.setOriginalImage(inputImagePath);
            imageRecord.setPredictedImage(outputDir + resultFileName);
            imageRecord.setRecognitionWeight(weightsName);
            imageRecord.setMinThreshold(0.5); // 示例值，根据实际情况设置
            imageRecord.setAiAssistant("使用AI"); // 示例值，根据实际情况设置
            imageRecord.setAiSuggestion("建议使用更高权重"); // 示例值，根据实际情况设置
            imageRecord.setRecognitionTime(new Date());
            imageRecord.setRecognitionUser("admin"); // 示例值，根据实际情况设置
            imageRecord.setOperation("删除");

            boolean saveResult = imageRecordService.saveImageRecord(imageRecord);
            if (!saveResult) {
                return ApiResult.failed("保存识别记录失败");
            }

            ApiResult<String> result = ApiResult.success("检测成功");
            result.setData(resultFileName);  // 前端可根据 fileName 拼出 result 图路径
            return result;
        } catch (IOException | InterruptedException e) {
            return ApiResult.failed("检测过程出错：" + e.getMessage());
        }
    }



}
