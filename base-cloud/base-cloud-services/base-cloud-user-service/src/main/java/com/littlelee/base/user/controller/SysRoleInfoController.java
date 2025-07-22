package com.littlelee.base.user.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.vo.SysRoleVo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.po.SysRoleInfo;
import com.littlelee.base.user.model.query.SysRoleInfoQuery;
import com.littlelee.base.user.service.SysRoleInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: littlelee
 * @date 2018年11月01日15:15:54
 */
@RestController
@RequestMapping("/role")
@Tag(name = "角色操作接口", description = "角色 controller")
public class SysRoleInfoController {

    private static final String MODULE_NAME = "系统角色模块";

    @Autowired
    private SysRoleInfoService sysRoleService;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "添加角色")
    @Operation(summary = "添加角色", description = "角色信息")
    @PostMapping
    public ApiResult<Boolean> save(
            @Parameter(description = "角色信息", required = true)
            @RequestBody SysRoleInfo role) {
        role.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysRoleService.save(role));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "修改角色")
    @Operation(summary = "修改角色", description = "角色信息")
    @PutMapping
    public ApiResult<Boolean> update(
            @Parameter(description = "角色信息", required = true)
            @RequestBody SysRoleInfo role) {
        role.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysRoleService.updateById(role));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "删除角色")
    @Operation(summary = "删除角色", description = "删除角色信息")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(
            @Parameter(description = "角色id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(sysRoleService.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "查询角色信息以及相关联的资源信息")
    @Operation(summary = "查询角色信息", description = "查询角色信息以及相关联的资源信息")
    @GetMapping("/{id}")
    public ApiResult<SysRoleInfo> getSysRoleInfo(
            @Parameter(description = "角色id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(sysRoleService.getRoleInfoWithMenuByRoleId(id));
    }

    @Operation(summary = "角色信息分页查询", description = "角色信息分页查询")
    @GetMapping("/page")
    public ApiResult<SysRoleInfoQuery> pageByQuery(
            @Parameter(description = "角色信息查询类", required = false)
            SysRoleInfoQuery sysRoleQuery) {
        sysRoleQuery.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysRoleService.pageByQuery(sysRoleQuery));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "查询所有角色信息")
    @Operation(summary = "查询所有角色信息", description = "查询角色信息")
    @GetMapping
    public ApiResult<List<SysRoleInfo>> listRole() {
        return new ApiResult<>(sysRoleService.listSysRole(UserUtil.getAppId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "根据角色编码查询角色信息")
    @Operation(summary = "根据角色编码查询角色信息", description = "根据角色编码查询角色信息")
    @GetMapping("/queryRolesByRoleCodes/{roleCodes}/{appId}")
    public List<SysRoleVo> queryRolesByRoleCodes(
            @Parameter(description = "角色编码列表（逗号分隔）", required = true)
            @PathVariable("roleCodes") String roleCodes,
            @Parameter(description = "应用ID", required = true)
            @PathVariable("appId") String appId) {
        return sysRoleService.queryRolesByRoleCodes(appId, roleCodes);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "根据角色编码查询剩余角色信息")
    @Operation(summary = "根据角色编码查询剩余角色信息", description = "根据角色编码查询剩余角色信息")
    @GetMapping("/queryExistRolesByRoleCodes/{roleCodes}/{appId}")
    public List<SysRoleVo> queryExistRolesByRoleCodes(
            @Parameter(description = "角色编码列表（逗号分隔）", required = true)
            @PathVariable("roleCodes") String roleCodes,
            @Parameter(description = "应用ID", required = true)
            @PathVariable("appId") String appId) {
        return sysRoleService.queryExistRolesByRoleCodes(appId, roleCodes);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "查询所有角色信息")
    @Operation(summary = "查询所有角色信息", description = "查询所有角色信息")
    @GetMapping("/queryRoleVos/{appId}")
    public List<SysRoleVo> queryRoleVos(
            @Parameter(description = "应用ID", required = true)
            @PathVariable("appId") String appId) {
        return sysRoleService.queryRoleVos(appId);
    }
}