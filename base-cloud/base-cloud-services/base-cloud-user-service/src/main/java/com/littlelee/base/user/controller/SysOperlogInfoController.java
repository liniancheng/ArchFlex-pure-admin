package com.littlelee.base.user.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.model.bo.SysOperlog;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.user.model.po.SysOperlogInfo;
import com.littlelee.base.user.model.query.SysOperlogInfoQuery;
import com.littlelee.base.user.model.query.SysOperlogTextQuery;
import com.littlelee.base.user.service.SysOperlogInfoService;
import com.littlelee.base.user.service.impl.SysOperlogInfoServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@EnableAsync
@RestController
@RequestMapping("/log")
@Tag(name = "系统日志操作接口", description = "日志 controller")
public class SysOperlogInfoController {

    private static final String MODULE_NAME = "系统日志模块";

    @Autowired
    private SysOperlogInfoService sysLogService;

    @Operation(summary = "日志信息分页查询", description = "日志信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysOperlogInfoQuery> pageByQuery(
            @Parameter(description = "日志信息查询类", required = false)
            SysOperlogInfoQuery sysLogQuery) {
        return new ApiResult<>(sysLogService.pageByQuery(sysLogQuery));
    }

    @Operation(summary = "日志下载分页查询", description = "日志下载分页查询")
    @GetMapping("/txtPage")
    public ApiResult<SysOperlogTextQuery> pageTextByQuery(
            @Parameter(description = "日志下载查询类", required = false)
            SysOperlogTextQuery sysLogQuery) {
        return new ApiResult<>(sysLogService.pageTextByQuery(sysLogQuery));
    }

    @Operation(summary = "删除操作日志", description = "删除操作日志信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "操作日志id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(sysLogService.removeById(id));
    }

    @Operation(summary = "批量删除操作日志", description = "批量删除操作日志信息")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(
            @Parameter(description = "操作日志ids", required = true)
            @RequestBody List<String> ids) {
        return new ApiResult<>(sysLogService.removeByIds(ids));
    }

    @Operation(summary = "批量下载操作日志", description = "批量下载操作日志信息")
    @PostMapping("/download")
    public void download(
            @Parameter(description = "操作日志ids", required = true)
            @RequestBody List<String> ids,
            HttpServletResponse resp) {
        sysLogService.download(ids, resp);
    }

    @Operation(summary = "根据文件名下载操作日志", description = "下载指定名称的日志文件")
    @GetMapping("/downloadTxt/{name}")
    public void downloadTxt(
            @Parameter(description = "日志文件名关键字", required = true)
            @PathVariable("name") String name,
            HttpServletResponse response) throws IOException {

        List<File> files = searchFiles(new File(SysOperlogInfoServiceImpl.basePath), name);
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

        File[] subFolders = folder.listFiles(f ->
                f.isDirectory() || f.getName().toLowerCase().contains(keyword));

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

    @PostMapping("/save")
    public Future<Boolean> save(@RequestBody SysOperlog operLog) {
        SysOperlogInfo info = new SysOperlogInfo();
        BeanUtils.copyProperties(operLog, info);
        info.setCreateTime(new Date());
        return sysLogService.saveOperLog(info);
    }
}