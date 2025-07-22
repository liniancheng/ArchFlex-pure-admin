package com.littlelee.base.detect.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.model.bo.DetectVideo;
import com.littlelee.base.detect.service.ImageService;
import com.littlelee.base.detect.service.VideoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/video")
@Tag(name = "视频检测Controller", description = "视频检测操作接口")
public class VideoController {

    private static final String FUNC_NAME = "视频检测功能";

    private static final String UPLOAD_DIR = "D:\\work\\tobacco\\upload\\";  // 注意路径分隔符使用双反斜杠

    @Autowired
    private ImageService imageService;  // 注入 ImageService
    @Autowired
    private VideoService videoService;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "上传文件")
    @Operation(summary = "上传文件", description = "上传文件到服务器", method = "POST")
    @Parameter(name = "file", description = "上传的文件", required = true, schema = @Schema(implementation = MultipartFile.class))
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

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "视频检测")
    @Operation(summary = "视频检测", description = "使用指定模型和权重文件检测上传的视频", method = "POST")
    @PostMapping("/detect")
    public ApiResult<Map<String, Object>> detectVideo(@RequestBody DetectVideo detectVideo) {
        Map<String, Object> resultData = videoService.detectVideo(detectVideo);
        if (resultData != null) {
            return new ApiResult<>(resultData, ResponseCodeEnum.SUCCESS);
        } else {
            return ApiResult.failed("视频检测失败");
        }
    }
}