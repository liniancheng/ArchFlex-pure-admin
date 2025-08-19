package com.littlelee.base.user.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.model.vo.SysMenuVo;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.bo.SysMenuTree;
import com.littlelee.base.user.model.po.SysMenuInfo;
import com.littlelee.base.user.service.SysMenuInfoService;
import com.littlelee.base.user.util.TreeUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author: littlelee
 * @date: 2018/10/17 13:22
 * @description: 菜单管理
 */
@RestController
@RequestMapping("/resource")
@Tag(name = "菜单操作接口", description = "菜单 controller")
public class SysMenuInfoController {

    private static final String MODULE_NAME = "系统菜单模块";

    @Autowired
    private SysMenuInfoService sysMenuInfoService;
    @Autowired
    private HttpServletRequest request;

    @Operation(summary = "获取当前用户的菜单树", description = "根据token查询当前用户权限的菜单树")
    @GetMapping("/menu/tree")
    public ApiResult<List<SysMenuTree>> getMenuTree() {
        return new ApiResult<>(sysMenuInfoService.getMenuTreeByRoleCodes(
                UserUtil.getRoleCodes(request),
                UserUtil.getAppId(request)));
    }

    @Operation(summary = "获取所有菜单的树", description = "获取所有菜单的树")
    @GetMapping("/tree")
    public ApiResult<List<SysMenuTree>> getAllMenuTree() {
        return new ApiResult<>(sysMenuInfoService.getAllMenuTree(UserUtil.getAppId(request)));
    }

    @Operation(summary = "获取所有菜单的树（节点形式）", description = "获取所有菜单的树")
    @GetMapping("/treeNode")
    public ApiResult<List<TreeNode>> getAllMenuTreeNode() {
        return new ApiResult<>(sysMenuInfoService.getAllMenuTreeNode(UserUtil.getAppId(request)));
    }

    @Operation(summary = "获取所有菜单的列表（一维数组）", description = "获取所有菜单的列表")
    @GetMapping("/menu/list")
    public ApiResult<List<SysMenuInfo>> getAllMenuList() {
        return new ApiResult<>(sysMenuInfoService.getAllMenuList(UserUtil.getAppId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "添加菜单信息")
    @Operation(summary = "添加菜单信息", description = "添加菜单信息")
    @PostMapping
    public ApiResult<Boolean> saveMenu(
            @Parameter(description = "菜单信息", required = true)
            @RequestBody SysMenuInfo menu) {
        menu.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysMenuInfoService.save(menu));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "修改菜单信息")
    @Operation(summary = "修改菜单信息", description = "修改菜单信息")
    @PutMapping
    public ApiResult<Boolean> updateMenu(
            @Parameter(description = "菜单信息", required = true)
            @RequestBody SysMenuInfo menu) {
        menu.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysMenuInfoService.updateById(menu));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "根据id查询菜单信息")
    @Operation(summary = "查询菜单信息", description = "根据id查询菜单信息")
    @GetMapping("/id/{id}")
    public ApiResult<SysMenuVo> getById(
            @Parameter(description = "菜单id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(TreeUtil.copyMenuInfoToMenuVo(sysMenuInfoService.getById(id)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
            moduleName = MODULE_NAME, actionName = "根据id删除菜单信息")
    @Operation(summary = "删除菜单信息", description = "根据id删除菜单信息")
    @DeleteMapping("/id/{id}")
    public ApiResult<Boolean> deleteMenu(
            @Parameter(description = "菜单id", required = true)
            @PathVariable("id") String id) {
        return new ApiResult<>(sysMenuInfoService.deleteMenu(id, UserUtil.getAppId(request)));
    }

    /**
     * 对内服务，不包装 ApiResult
     * 根据角色查询菜单信息
     */
    @Operation(summary = "根据角色查询菜单信息", description = "根据角色查询菜单信息")
    @GetMapping("/role/{roleCode}")
    public Set<SysMenuVo> listResourceByRole(
            @Parameter(description = "角色code", required = true)
            @PathVariable("roleCode") String roleCode) {
        List<SysMenuInfo> menus = sysMenuInfoService.findMenuListByRoleCode(roleCode, UserUtil.getAppId(request));
        Set<SysMenuVo> menuVos = new HashSet<>();
        menus.forEach(menu -> menuVos.add(TreeUtil.copyMenuInfoToMenuVo(menu)));
        return menuVos;
    }

    @Operation(summary = "查询所有的菜单信息", description = "查询所有的菜单信息")
    @GetMapping("/listAllMenuInfo/{appId}")
    public Set<SysMenuVo> listAllMenuInfo(
            @Parameter(description = "应用ID", required = true)
            @PathVariable("appId") String appId) {
        List<SysMenuInfo> menus = sysMenuInfoService.listAllMenuInfo(appId);
        Set<SysMenuVo> menuVos = new HashSet<>();
        menus.forEach(menu -> menuVos.add(TreeUtil.copyMenuInfoToMenuVo(menu)));
        return menuVos;
    }
}