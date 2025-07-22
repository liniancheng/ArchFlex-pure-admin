package com.littlelee.base.anno.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.anno.model.po.SysAnnoTypeInfo;
import com.littlelee.base.anno.model.query.SysAnnoTypeInfoQuery;
import com.littlelee.base.anno.service.SysAnnoTypeInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author littlelee
 * @date 2019-11-26 09:51:11
 */
@RestController
@RequestMapping("/annoType")
@Tag(name = "公告类型操作接口", description = "公告类型 controller")
public class SysAnnoTypeInfoController {

    private static final String FUNC_NAME = "公告类型功能";

    @Autowired
    private SysAnnoTypeInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME,
            actionName = "添加公告类型")
    @Operation(summary = "添加公告类型", description = "公告类型信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "公告类型信息", required = true)
            @RequestBody SysAnnoTypeInfo annoType) {
        annoType.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlySave(annoType));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME,
            actionName = "修改公告类型")
    @Operation(summary = "修改公告类型", description = "公告类型信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "公告类型信息", required = true)
            @RequestBody SysAnnoTypeInfo annoType) {
        annoType.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlyUpdate(annoType));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME,
            actionName = "删除公告类型")
    @Operation(summary = "删除公告类型", description = "删除公告类型信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "公告类型id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME,
            actionName = "通过主键查询公告类型信息")
    @Operation(summary = "查询公告类型信息", description = "通过主键查询公告类型信息")
    @GetMapping("/{id}")
    public ApiResult<SysAnnoTypeInfo> getById(
            @Parameter(description = "公告类型id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "公告类型信息分页查询", description = "公告类型信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysAnnoTypeInfoQuery> pageByQuery(
            @Parameter(description = "公告类型信息查询类", required = false)
            SysAnnoTypeInfoQuery query) {
        query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "公告类型信息查询全部", description = "公告类型信息全部查询")
    @GetMapping("/findAll")
    public ApiResult<List<SysAnnoTypeInfo>> findAll() {
        return new ApiResult<>(service.findAll(UserUtil.getAppId(request)));
    }
}