package com.littlelee.base.detect.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.detect.config.DetectConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author littlelee
 * @date 2025-07-31
 */
@RestController
@RequestMapping("/files")
@Tag(name = "文件上传Controller", description = "文件上传操作接口")
public class FileController {

    private static final String FUNC_NAME = "文件上传功能";

    private SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_DETECT_SERVICE, moduleName = FUNC_NAME, actionName = "上传文件")
    @Operation(summary = "上传文件", description = "上传文件到服务器", method = "POST")
    @Parameter(name = "file", description = "上传的文件", required = true)
    @PostMapping("/upload")
    public ApiResult<?> upload(MultipartFile file, HttpServletRequest req) {
        // 检查文件是否为空
        if (file.isEmpty()) {
            return ApiResult.failed("文件为空，请选择一个文件");
        }

        // 获取文件的原始名称
        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            return ApiResult.failed("文件名称为空");
        }
        // 定义文件的唯一标识（前缀）
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

}
