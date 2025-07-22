package com.littlelee.base.resource.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.model.vo.SysDblinkVo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.resource.model.po.SysDblinkInfo;
import com.littlelee.base.resource.model.query.SysDblinkInfoQuery;
import com.littlelee.base.resource.service.SysDblinkInfoService;


import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuzhh
 * @date 2019-11-22 14:07:28
 */
@RestController
@RequestMapping("/dblink")
@Tag(name = "数据源管理操作接口", description = "数据源管理相关操作")
public class SysDblinkInfoController {
    private static final String MODULE_NAME = "数据源管理模块";

    @Autowired
    private SysDblinkInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加数据源管理")
    @Operation(summary = "添加数据源管理", description = "新增数据源管理信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "数据源管理信息", required = true)
            @RequestBody SysDblinkInfo dblinkInfo) {
        try {
            return new ApiResult<>(service.save(dblinkInfo));
        } catch (ServiceException e) {
            return new ApiResult<>().failed(e);
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改数据源管理")
    @Operation(summary = "修改数据源管理", description = "更新数据源管理信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "数据源管理信息", required = true)
            @RequestBody SysDblinkInfo dblinkInfo) {
        try {
            return new ApiResult<>(service.updateById(dblinkInfo));
        } catch (ServiceException e) {
            return new ApiResult<>().failed(e);
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除数据源管理")
    @Operation(summary = "删除数据源管理", description = "根据主键删除数据源管理")
    @DeleteMapping("/delete/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "数据源管理id", required = true)
            @PathVariable("id") String id) {
        try {
            return new ApiResult<>(service.removeById(id));
        } catch (ServiceException e) {
            return new ApiResult<>().failed(e);
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询数据源管理信息以及相关联的资源信息")
    @Operation(summary = "查询数据源管理信息", description = "查询数据源管理信息以及相关联的资源信息")
    @GetMapping("/getById/{id}")
    public ApiResult<SysDblinkInfo> getById(
            @Parameter(description = "数据源管理id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "数据源管理信息分页查询", description = "分页条件查询数据源管理列表")
    @GetMapping("/page")
    public ApiResult<SysDblinkInfoQuery> pageByQuery(
            @Parameter(description = "查询条件") SysDblinkInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "数据源测试")
    @Operation(summary = "数据源测试", description = "测试指定数据源连通性")
    @GetMapping("/test/{id}")
    public ApiResult<Boolean> test(
            @Parameter(description = "数据源id", required = true)
            @PathVariable("id") String id) {
        try {
            return new ApiResult<>(service.test(id));
        } catch (ServiceException e) {
            return new ApiResult<>().failed(e);
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "可用数据源列表")
    @Operation(summary = "可用数据源列表", description = "获取可用数据源树形列表")
    @GetMapping("/list")
    public ApiResult<List<TreeNode>> list() {
        try {
            return new ApiResult<>(service.listDb());
        } catch (ServiceException e) {
            return new ApiResult<>().failed(e);
        }
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "可用数据源列表")
    @Operation(summary = "可用数据源列表", description = "获取可用数据源视图列表")
    @GetMapping("/listDbVo")
    public List<SysDblinkVo> listDbVo() {
        return service.listDbVo();
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询数据源信息")
    @Operation(summary = "查询数据源管理信息", description = "根据主键获取数据源视图对象")
    @GetMapping("/getVoById/{id}")
    public SysDblinkVo getVoById(
            @Parameter(description = "数据源管理id", required = true)
            @PathVariable("id") String id) {
        return service.getVoById(id);
    }
}