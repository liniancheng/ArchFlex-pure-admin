package com.littlelee.base.user.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.vo.SysResInfoVo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysResInfo;
import com.littlelee.base.user.model.query.SysResInfoQuery;
import com.littlelee.base.user.service.SysResInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author littlelee
 * @date 2022-03-29 14:14:04
 */
@RestController
@RequestMapping("/resall")
@Tag(name = "资源配置操作接口", description = "资源配置 controller")
public class SysResInfoController {

    private static final String FUNC_NAME = "资源配置功能";

    @Autowired
    private SysResInfoService service;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "添加资源配置")
    @Operation(summary = "添加资源配置", description = "资源配置信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "资源配置信息", required = true)
            @RequestBody SysResInfo resall) {
        resall.setAppId(UserUtil.getAppId(request));
        resall.setCreatedTime(new Date());
        return new ApiResult<>(service.save(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "测试资源配置")
    @Operation(summary = "测试资源配置", description = "测试资源配置")
    @PostMapping("/test")
    public ApiResult<Boolean> test(
            @Parameter(description = "资源配置信息", required = true)
            @RequestBody SysResInfo resall) {
        return new ApiResult<>(service.test(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "修改资源配置")
    @Operation(summary = "修改资源配置", description = "资源配置信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "资源配置信息", required = true)
            @RequestBody SysResInfo resall) {
        resall.setModifyTime(new Date());
        return new ApiResult<>(service.updateById(resall));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "删除资源配置")
    @Operation(summary = "删除资源配置", description = "删除资源配置信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "资源配置id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = FUNC_NAME, actionName = "通过主键查询资源配置信息")
    @Operation(summary = "查询资源配置信息", description = "通过主键查询资源配置信息")
    @GetMapping("/{id}")
    public ApiResult<SysResInfo> getById(
            @Parameter(description = "资源配置id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(service.getById(id));
    }

    @Operation(summary = "资源配置信息分页查询", description = "资源配置信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysResInfoQuery> pageByQuery(
            @Parameter(description = "资源配置信息查询类", required = false)
            SysResInfoQuery query) {
        return new ApiResult<>(service.pageByQuery(query));
    }

    @Operation(summary = "资源配置信息列表查询", description = "资源配置信息列表查询")
    @GetMapping("/list")
    public ApiResult<List<SysResInfo>> list() {
        return new ApiResult<>(service.fetchList());
    }

    @GetMapping("/selectById/{resVal}")
    public SysResInfoVo queryById(
            @Parameter(description = "资源id", required = true)
            @PathVariable("resVal") String resVal) {
        SysResInfo sysResInfo = service.getById(resVal);
        SysResInfoVo vo = new SysResInfoVo();
        if (sysResInfo != null) {
            BeanUtils.copyProperties(sysResInfo, vo);
        }
        return vo;
    }
}