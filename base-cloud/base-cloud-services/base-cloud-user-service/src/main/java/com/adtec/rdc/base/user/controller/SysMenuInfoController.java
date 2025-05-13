package com.adtec.rdc.base.user.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.model.vo.SysMenuVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.bo.SysMenuTree;
import com.adtec.rdc.base.user.model.po.SysMenuInfo;
import com.adtec.rdc.base.user.service.SysMenuInfoService;
import com.adtec.rdc.base.user.util.TreeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: JTao
 * @date: 2018/10/17 13:22
 * @description: 菜单管理
 */
@RestController
@RequestMapping("/resource")
@Api(value = "菜单controller", tags = {"菜单操作接口"})
public class SysMenuInfoController {

    private static final String MODULE_NAME = "系统菜单模块";

    @Autowired
    private SysMenuInfoService sysMenuInfoService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取当前用户的菜单树
     * @return
     */
    @ApiOperation(value = "获取当前用户的菜单树", notes = "根据token查询当前用户权限的菜单树", httpMethod = "GET")
    @GetMapping("/menu/tree")
    public ApiResult<List<SysMenuTree>> getMenuTree(){
        List<String> roleCodes = UserUtil.getRoleCodes(request);
        return new ApiResult<>(sysMenuInfoService.getMenuTreeByRoleCodes(roleCodes, UserUtil.getAppId(request)));
    }

    /**
     * 获取所有的菜单树
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "获取所有菜单的树", notes = "获取所有菜单的树", httpMethod = "GET")
    public ApiResult<List<SysMenuTree>> getAllMenuTree(){
        return new ApiResult<>(sysMenuInfoService.getAllMenuTree(UserUtil.getAppId(request)));
    }

    /**
     * 获取所有的菜单树
     * @return
     */
    @GetMapping("/treeNode")
    @ApiOperation(value = "获取所有菜单的树", notes = "获取所有菜单的树", httpMethod = "GET")
    public ApiResult<List<TreeNode>> getAllMenuTreeNode(){
        return new ApiResult<>(sysMenuInfoService.getAllMenuTreeNode(UserUtil.getAppId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加菜单信息")
    @ApiOperation(value = "添加菜单信息", notes = "添加菜单信息", httpMethod = "POST")
    @ApiImplicitParam(name = "menu", value = "菜单信息", required = true, dataType = "SysMenuVo")
    @PostMapping
    public ApiResult<Boolean> saveMenu(@RequestBody SysMenuVo menu) {
    	menu.setAppId(UserUtil.getAppId(request));
        return new ApiResult(sysMenuInfoService.save(TreeUtil.copyMenuVoToMenuInfo(menu)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改菜单信息")
    @ApiOperation(value = "修改菜单信息", notes = "修改菜单信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "menu", value = "菜单信息", required = true, dataType = "SysMenuVo")
    @PutMapping
    public ApiResult<Boolean> updateMenu(@RequestBody SysMenuVo menu) {
    	menu.setAppId(UserUtil.getAppId(request));
        return new ApiResult(sysMenuInfoService.updateById((TreeUtil.copyMenuVoToMenuInfo(menu))));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据id查询菜单信息")
    @ApiOperation(value = "查询菜单信息", notes = "根据id查询菜单信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "string")
    @GetMapping("/id/{id}")
    public ApiResult<SysMenuVo> getById(@PathVariable("id") String id){
        return new ApiResult<>(TreeUtil.copyMenuInfoToMenuVo(sysMenuInfoService.getById(id)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据id删除菜单信息")
    @ApiOperation(value = "删除菜单信息", notes = "根据id删除菜单信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "菜单id", required = true, dataType = "string")
    @DeleteMapping("/id/{id}")
    public ApiResult<Boolean> deleteMenu(@PathVariable("id") String id) {
        return new ApiResult<>(sysMenuInfoService.deleteMenu(id, UserUtil.getAppId(request)));
    }

    /**
     * 对内服务 不用ApiResult包装
     * 根据角色查询菜单信息
     * @param roleCode
     */
    @ApiOperation(value = "根据角色查询菜单信息", notes = "根据角色查询菜单信息", httpMethod = "GET")
    @ApiImplicitParam(name = "roleCode", value = "角色code", required = true, dataType = "string")
    @GetMapping("/role/{roleCode}")
    public Set<SysMenuVo> listResourceByRole(@PathVariable("roleCode") String roleCode){
        List<SysMenuInfo> menus = sysMenuInfoService.findMenuListByRoleCode(roleCode,UserUtil.getAppId(request));
        Set<SysMenuVo> menuVos = new HashSet<>();
        menus.stream().forEach(menu -> {
        	menuVos.add(TreeUtil.copyMenuInfoToMenuVo(menu));
        });
        return menuVos;
    }
    
    @ApiOperation(value = "查询所有的菜单信息", notes = "查询所有的菜单信息", httpMethod = "GET")
    @GetMapping("/listAllMenuInfo/{appId}")
    public Set<SysMenuVo> listAllMenuInfo(@PathVariable("appId") String appId){
        List<SysMenuInfo> menus = sysMenuInfoService.listAllMenuInfo(appId);
        Set<SysMenuVo> menuVos = new HashSet<>();
        menus.stream().forEach(menu -> {
        	menuVos.add(TreeUtil.copyMenuInfoToMenuVo(menu));
        });
        return menuVos;
    }
}
