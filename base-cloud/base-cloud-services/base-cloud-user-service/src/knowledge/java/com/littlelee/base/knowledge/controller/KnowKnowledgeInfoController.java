package com.littlelee.base.knowledge.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeInfoQuery;
import com.littlelee.base.knowledge.service.KnowKnowledgeInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
@RestController
@RequestMapping("/knowledge")
@Tag(name = "知识库管理操作接口", description = "知识库管理 controller")
public class KnowKnowledgeInfoController {

    private static final String FUNC_NAME = "知识库管理功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private KnowKnowledgeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加知识库管理")
    @Operation(summary = "添加知识库管理", description = "知识库管理信息")
    @PostMapping
    public ApiResult<String> save(
            @Parameter(description = "知识库管理信息", required = true)
            @RequestBody KnowKnowledgeInfo knowledge) {
        knowledge.setAppId(UserUtil.getAppId(request));
        knowledge.setCreateUser(UserUtil.getUserId(request));
        return new ApiResult<>(service.onlySave(knowledge));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改知识库管理")
    @Operation(summary = "修改知识库管理", description = "知识库管理信息")
    @PutMapping
    public ApiResult<String> update(
            @Parameter(description = "知识库管理信息", required = true)
            @RequestBody KnowKnowledgeInfo knowledge) {
        knowledge.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlyUpdate(knowledge));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除知识库管理")
    @Operation(summary = "删除知识库管理", description = "删除知识库管理信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "知识库管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询知识库管理信息")
    @Operation(summary = "查询知识库管理信息", description = "通过主键查询知识库管理信息")
    @GetMapping("/{id}")
    public ApiResult<KnowKnowledgeInfo> getById(
            @Parameter(description = "知识库管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.fineById(id));
    }

    @Operation(summary = "知识库管理信息分页查询", description = "知识库管理信息分页查询")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeInfoQuery> pageByQuery(
            @Parameter(description = "知识库管理信息查询类", required = false)
            KnowKnowledgeInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
}