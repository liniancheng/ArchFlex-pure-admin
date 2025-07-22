package com.littlelee.base.migrate.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.migrate.model.bo.SysMigrateTree;
import com.littlelee.base.migrate.model.po.SysMigrateInfo;
import com.littlelee.base.migrate.model.query.SysMigrateInfoQuery;
import com.littlelee.base.migrate.service.SysMigrateInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@RestController
@RequestMapping("/migrate")
@Tag(name = "导入导出信息表操作接口", description = "导入导出信息表 controller")
public class SysMigrateInfoController {

    private static final String FUNC_NAME = "导入导出信息表功能";

    @Autowired
    private SysMigrateInfoService service;

    /* ---------- 基础 CRUD ---------- */
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加导入导出信息表")
    @Operation(summary = "添加导入导出信息表", description = "导入导出信息表信息")
    @PostMapping
    public ApiResult<Boolean> save(@Parameter(description = "导入导出信息表信息", required = true) @RequestBody SysMigrateInfo migrate) {
        return new ApiResult<>(service.onlySave(migrate));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改导入导出信息表")
    @Operation(summary = "修改导入导出信息表", description = "导入导出信息表信息")
    @PutMapping
    public ApiResult<Boolean> update(@Parameter(description = "导入导出信息表信息", required = true) @RequestBody SysMigrateInfo migrate) {
        return new ApiResult<>(service.onlyUpdateById(migrate));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除导入导出信息表")
    @Operation(summary = "删除导入导出信息表", description = "删除导入导出信息表信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@Parameter(description = "导入导出信息表id", required = true) @PathVariable String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "查询导入导出信息表信息")
    @Operation(summary = "查询导入导出信息表信息", description = "通过主键查询导入导出信息表信息")
    @GetMapping("/{id}")
    public ApiResult<SysMigrateInfo> getById(@Parameter(description = "导入导出信息表id", required = true) @PathVariable String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "导入导出信息表信息分页查询", description = "导入导出信息表信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysMigrateInfoQuery> pageByQuery(@Parameter(description = "查询条件", required = false) SysMigrateInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    /* ---------- 树形结构 ---------- */
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "按父节点ID加载资源节点")
    @Operation(summary = "按父节点ID加载资源节点", description = "根据父节点ID获取树形节点列表")
    @GetMapping("/getTreeNodes/{pId}")
    public ApiResult<List<SysMigrateTree>> getTreeNodes(@Parameter(description = "父节点id", required = true) @PathVariable String pId) {
        return new ApiResult<>(service.getTreeNodes(pId));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "获取资源树全部节点")
    @Operation(summary = "获取资源树全部节点", description = "获取完整的资源树节点列表")
    @GetMapping("/getAllTreeNodes")
    public ApiResult<List<SysMigrateTree>> getAllTreeNodes() {
        return new ApiResult<>(service.getAllTreeNodes());
    }
}