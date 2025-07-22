package com.littlelee.base.knowledge.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeAttachInfoQuery;
import com.littlelee.base.knowledge.service.KnowKnowledgeAttachInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
@RestController
@RequestMapping("/att")
@Tag(name = "附件下载操作接口", description = "附件下载 controller")
public class KnowKnowledgeAttachInfoController {

    private static final String FUNC_NAME = "附件下载功能";

    @Autowired
    private KnowKnowledgeAttachInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "知识库附件上传")
    @Operation(summary = "知识库附件上传", description = "知识库附件上传")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "知识库ID", required = true)
            @RequestParam("knowledgeId") String knowledgeId,
            @Parameter(description = "附件文件", required = true)
            @RequestParam("file") MultipartFile[] file) {
        return new ApiResult<>(service.onlySave(knowledgeId, file, UserUtil.getAppId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除附件下载")
    @Operation(summary = "删除附件下载", description = "删除附件下载信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "附件下载id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @Operation(summary = "附件下载信息分页查询", description = "附件下载信息分页查询")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeAttachInfoQuery> pageByQuery(
            @Parameter(description = "附件下载信息查询类", required = false)
            KnowKnowledgeAttachInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "下载知识库附件")
    @Operation(summary = "下载知识库附件", description = "下载知识库附件")
    @GetMapping("/download/{id}")
    public void download(
            @Parameter(description = "知识库附件信息表id", required = true)
            @PathVariable("id") String id,
            HttpServletResponse response) throws IOException {

        KnowKnowledgeAttachInfo annoAttach = service.getById(id);
        response.reset();
        response.addHeader("Content-Length", "" + annoAttach.getAttContent().length);
        response.addHeader("Content-Disposition", "attachment;fileName=" + annoAttach.getAttName());
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(annoAttach.getAttContent(), response.getOutputStream());
    }

    @Operation(summary = "知识库附件列表", description = "知识库附件列表")
    @GetMapping("/list/{id}")
    public ApiResult<List<KnowKnowledgeAttachInfo>> list(
            @Parameter(description = "知识库ID", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getByKnowledgeId(id));
    }
}