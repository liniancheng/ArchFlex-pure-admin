package com.littlelee.base.detect.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.model.bo.DetectImage;
import com.littlelee.base.detect.model.bo.DetectImageBatch;
import com.littlelee.base.detect.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author littlelee
 * @date 2025-07-29
 */
@RestController
@RequestMapping("/batch")
@Tag(name = "批量检测Controller", description = "批量检测操作接口")
public class BatchController {

    private static final String FUNC_NAME = "批量检测功能";

    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @Autowired
    private ImageService imageService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "批量上传文件")
    @Operation(summary = "批量上传文件", description = "批量上传多个文件到服务器", method = "POST")
    @PostMapping("/upload")
    public ApiResult<List<String>> batchUpload(@RequestParam("files") List<MultipartFile> files, HttpServletRequest req) {
        if (files == null || files.isEmpty()) {
            return ApiResult.failed("未选择任何文件");
        }

        String format = sdf.format(new Date());
        String path = DetectConfig.getProfile() + format;
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        List<String> urlList = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String oldName = file.getOriginalFilename();
                if (oldName == null) continue;

                String newName = System.currentTimeMillis() + oldName.substring(oldName.lastIndexOf("."));
                file.transferTo(new File(folder, newName));

                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploads" + format + newName;
                urlList.add(url);
            } catch (IOException e) {
                e.printStackTrace();
                return ApiResult.failed("文件上传失败：" + e.getMessage());
            }
        }

        return ApiResult.success(urlList);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "批量图像检测")
    @Operation(summary = "批量图像检测", description = "批量检测已上传的图像", method = "POST")
    @PostMapping("/detect")
    public ApiResult<Map<String, Object>> batchDetect(@RequestBody DetectImageBatch detectImageBatch) {
        List<String> imageUrls = detectImageBatch.getImageUrls();
        if (imageUrls == null || imageUrls.isEmpty()) {
            return ApiResult.failed("未提供任何图片 URL");
        }

        Map<String, Object> allResults = new LinkedHashMap<>();
        for (String url : imageUrls) {
            DetectImage detectImage = new DetectImage();
            detectImage.setOriginalImage(url); // 如果支持URL推理
            detectImage.setModel(detectImageBatch.getModel());
            detectImage.setRecognitionWeight(detectImageBatch.getRecognitionWeight());
            detectImage.setMinThreshold(detectImageBatch.getMinThreshold());
            detectImage.setAiAssistant(detectImageBatch.getAiAssistant());

            try {
                Map<String, Object> result = imageService.detectImage(detectImage);
                allResults.put(url, result);
            } catch (Exception e) {
                allResults.put(url, "检测失败: " + e.getMessage());
            }
        }

        return new ApiResult<>(allResults, ResponseCodeEnum.SUCCESS);
    }



}
