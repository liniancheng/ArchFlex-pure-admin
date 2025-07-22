package com.littlelee.base.user.controller;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysHelpFileInfo;
import com.littlelee.base.user.model.query.SysHelpFileQuery;
import com.littlelee.base.user.service.SysHelpFileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/help")
@Tag(name = "帮助文档接口", description = "帮助文档 controller")
public class SysHelpFileController {

    private static final String FUNC_NAME = "帮助文档";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysHelpFileService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除帮助文档")
    @Operation(summary = "删除帮助文档", description = "删除帮助文档信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "帮助文档id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @Operation(summary = "查询帮助文档信息", description = "通过主键查询帮助文档信息")
    @GetMapping("/{id}")
    public ApiResult<SysHelpFileInfo> getById(
            @Parameter(description = "帮助文档id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "帮助文档信息分页查询", description = "帮助文档信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysHelpFileQuery> pageByQuery(
            @Parameter(description = "帮助文档信息查询类", required = false)
            SysHelpFileQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "下载帮助文档")
    @Operation(summary = "帮助文档下载", description = "帮助文档下载")
    @GetMapping("/download/{id}")
    public void download(
            @Parameter(description = "文件id", required = true)
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {
        SysHelpFileInfo helpFile = service.getById(id);
        response.reset();
        response.addHeader("Content-Length", String.valueOf(helpFile.getFileContent().length));
        response.addHeader("Content-Disposition", "attachment;filename=" + helpFile.getFileName());
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(helpFile.getFileContent(), response.getOutputStream());
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "上传帮助文档")
    @Operation(summary = "上传帮助文档", description = "上传帮助文档")
    @PostMapping
    public ApiResult<String> save(
            @Parameter(description = "上传帮助文档", required = true)
            @RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        return new ApiResult<>(service.upload(file));
    }
}