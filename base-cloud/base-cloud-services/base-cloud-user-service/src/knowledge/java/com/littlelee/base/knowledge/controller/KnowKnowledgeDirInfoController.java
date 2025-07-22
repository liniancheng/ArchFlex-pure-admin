package com.littlelee.base.knowledge.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.knowledge.model.bo.KnowKnowledgeDirInfoTree;
import com.littlelee.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.littlelee.base.knowledge.model.query.KnowKnowledgeDirInfoQuery;
import com.littlelee.base.knowledge.service.KnowKnowledgeDirInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@RestController
@RequestMapping("/dir")
@Tag(name = "知识库类型操作接口", description = "知识库类型 controller")
public class KnowKnowledgeDirInfoController {

    private static final String FUNC_NAME = "知识库类型功能";

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private KnowKnowledgeDirInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加知识库类型")
    @Operation(summary = "添加知识库类型", description = "知识库类型信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "知识库类型信息", required = true)
            @RequestBody KnowKnowledgeDirInfo dir) {
        dir.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(dir));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改知识库类型")
    @Operation(summary = "修改知识库类型", description = "知识库类型信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "知识库类型信息", required = true)
            @RequestBody KnowKnowledgeDirInfo dir) {
        dir.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(dir));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除知识库类型")
    @Operation(summary = "删除知识库类型", description = "删除知识库类型信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "知识库类型id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询知识库类型信息")
    @Operation(summary = "查询知识库类型信息", description = "通过主键查询知识库类型信息")
    @GetMapping("/{id}")
    public ApiResult<KnowKnowledgeDirInfo> getById(
            @Parameter(description = "知识库类型id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "知识库类型信息分页查询", description = "知识库类型信息分页查询")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeDirInfoQuery> pageByQuery(
            @Parameter(description = "知识库类型信息查询类", required = false)
            KnowKnowledgeDirInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    /**
     * 获取所有的类型树
     */
    @GetMapping("/tree")
    @Operation(summary = "获取所有类型的树", description = "获取所有类型的树")
    public ApiResult<List<KnowKnowledgeDirInfoTree>> getAllKnowledgeDirTree() {
        return new ApiResult<>(service.getAllKnowledgeTree());
    }

    /**
     * 获取所有的菜单树
     */
    @GetMapping("/treeNode")
    @Operation(summary = "获取所有类型的树", description = "获取所有类型的树")
    public ApiResult<List<TreeNode>> getAllKnowledgeTreeNode() {
        return new ApiResult<>(service.getAllKnowledgeNode());
    }
}