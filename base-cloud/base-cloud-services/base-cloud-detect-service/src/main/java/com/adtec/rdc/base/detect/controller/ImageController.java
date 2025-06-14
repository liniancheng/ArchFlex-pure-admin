package com.adtec.rdc.base.detect.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
/**
 * @author littlelee
 * @date 2025-06-14
 */
@RestController
@RequestMapping("/image")
@Api(value = "图像检测Controller", tags = {"图像检测操作接口"})
public class ImageController {

    private static final String FUNC_NAME = "图片检测功能";


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
        String uploadDir = "D:\\work\\upload\\"; // 注意路径分隔符使用双反斜杠
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs(); // 如果目录不存在，创建目录
        }

        // 保存文件到指定路径
        try {
            file.transferTo(new File(uploadDir + fileName));
            return ApiResult.success("文件上传成功，保存路径为：" + uploadDir + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return ApiResult.failed("文件上传失败：" + e.getMessage());
        }
    }

}
