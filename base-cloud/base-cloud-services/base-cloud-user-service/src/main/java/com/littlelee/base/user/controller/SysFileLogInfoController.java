package com.littlelee.base.user.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.user.model.query.SysOperlogTextQuery;
import com.littlelee.base.user.service.SysFileLogInfoService;
import com.littlelee.base.user.service.impl.SysFileLogInfoServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/logfile")
@Tag(name = "日志文件操作接口", description = "日志文件 controller")
public class SysFileLogInfoController {

    @Autowired
    private SysFileLogInfoService sysLogService;

    @Operation(summary = "日志下载分页查询", description = "日志下载分页查询")
    @GetMapping("/txtPage")
    public ApiResult<SysOperlogTextQuery> pageTextByQuery(
            @Parameter(description = "日志下载查询类", required = false)
            SysOperlogTextQuery sysLogQuery) {
        return new ApiResult<>(sysLogService.pageTextByQuery(sysLogQuery));
    }

    @Operation(summary = "批量下载操作日志", description = "下载操作日志信息")
    @GetMapping("/downloadTxt/{name}")
    public void downloadTxt(
            @Parameter(description = "日志文件名关键字", required = true)
            @PathVariable("name") String name,
            HttpServletResponse response) throws IOException {

        List<File> files = searchFiles(new File(SysFileLogInfoServiceImpl.basePath), name);
        if (files.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }
        File file = files.get(0);
        response.reset();
        response.addHeader("Content-Length", String.valueOf(file.length()));
        response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(getBytesByFile(file), response.getOutputStream());
    }

    private byte[] getBytesByFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream(1000)) {
            byte[] buffer = new byte[1000];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, n);
            }
            return bos.toByteArray();
        }
    }

    private List<File> searchFiles(File folder, final String keyword) {
        List<File> result = new ArrayList<>();
        if (folder.isFile()) {
            result.add(folder);
            return result;
        }

        File[] subFolders = folder.listFiles(file ->
                file.isDirectory() || file.getName().toLowerCase().contains(keyword));

        if (subFolders != null) {
            for (File file : subFolders) {
                if (file.isFile()) {
                    result.add(file);
                } else {
                    result.addAll(searchFiles(file, keyword));
                }
            }
        }
        return result;
    }
}