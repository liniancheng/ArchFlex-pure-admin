package com.littlelee.base.detect.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.enums.ResponseCodeEnum;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import com.littlelee.base.detect.model.bo.DetectImage;
import com.littlelee.base.detect.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/image")
@Tag(name = "图像检测Controller", description = "图像检测操作接口")
public class ImageController {

    private static final String FUNC_NAME = "图片检测功能";

    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @Autowired
    private ImageService imageService;  // 注入 ImageService

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "上传文件")
    @Operation(summary = "上传文件", description = "上传文件到服务器", method = "POST")
    @Parameter(name = "file", description = "上传的文件", required = true)
    @PostMapping("/upload")
    public ApiResult<String> uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest req) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ApiResult.failed("文件为空，请选择一个文件");
        }

        // 获取文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ApiResult.failed("文件名称为空");
        }

        String format = sdf.format(new Date());
        String path = DetectConfig.getProfile() + format;
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs(); // 如果目录不存在，创建目录
        }
        String oldName = file.getOriginalFilename();
        String newName = System.currentTimeMillis() + oldName.substring(oldName.lastIndexOf("."));

        // 保存文件到指定路径
        try {
            file.transferTo(new File(folder, newName));
            // http://localhost:8898/uploads/2025/07/11/test.jpg
            String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploads" + format + newName;
            ApiResult<String> result = ApiResult.success("文件上传成功");
            result.setData(url);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.failed("文件上传失败：" + e.getMessage());
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "图像检测")
    @Operation(summary = "图像检测", description = "使用指定模型和权重文件检测上传的图像", method = "POST")
    @PostMapping("/detect")
    public ApiResult<Map<String, Object>> detectImage(@RequestBody DetectImage detectImage) {
        Map<String, Object> resultData = imageService.detectImage(detectImage);
        if (resultData != null) {
            return new ApiResult<>(resultData, ResponseCodeEnum.SUCCESS);
        } else {
            return ApiResult.failed("图像检测失败");
        }
    }
}
