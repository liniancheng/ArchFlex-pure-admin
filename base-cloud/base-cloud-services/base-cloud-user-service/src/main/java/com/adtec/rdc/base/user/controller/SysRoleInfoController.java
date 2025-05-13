package com.adtec.rdc.base.user.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.vo.SysRoleVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.query.SysRoleInfoQuery;
import com.adtec.rdc.base.user.service.SysRoleInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: JTao
 * @date 2018年11月01日15:15:54
 */
@RestController
@RequestMapping("/role")
@Api(value = "角色controller", tags = {"角色操作接口"})
public class SysRoleInfoController {

    private static final String MODULE_NAME = "系统角色模块";

    @Autowired
    private SysRoleInfoService sysRoleService;
    @Autowired
    private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加角色")
    @ApiOperation(value = "添加角色", notes = "角色信息", httpMethod = "POST")
    @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "SysRoleInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysRoleInfo role){
    	role.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysRoleService.save(role));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改角色")
    @ApiOperation(value = "修改角色", notes = "角色信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "role", value = "角色信息", required = true, dataType = "SysRoleInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysRoleInfo role){
    	role.setAppId(UserUtil.getAppId(request));
    	return new ApiResult<>(sysRoleService.updateById(role));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除角色")
    @ApiOperation(value = "删除角色", notes = "删除角色信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(sysRoleService.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询角色信息以及相关联的资源信息")
    @ApiOperation(value = "查询角色信息", notes = "查询角色信息以及相关联的资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "角色id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysRoleInfo> getSysRoleInfo(@PathVariable("id") String id){
        return new ApiResult<>(sysRoleService.getRoleInfoWithMenuByRoleId(id));
    }

    @ApiOperation(value = "角色信息分页查询", notes = "角色信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "sysRoleQuery", value = "角色信息查询类", required = false, dataType = "SysRoleQuery")
    @GetMapping("/page")
    public ApiResult<SysRoleInfoQuery> pageByQuery(SysRoleInfoQuery sysRoleQuery){
    	sysRoleQuery.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(sysRoleService.pageByQuery(sysRoleQuery));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询所有角色信息")
    @ApiOperation(value = "查询所有角色信息", notes = "查询角色信息", httpMethod = "GET")
    @GetMapping
    public ApiResult<List<SysRoleInfo>> listRole(){
    	return new ApiResult<>(sysRoleService.listSysRole(UserUtil.getAppId(request)));
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据角色编码查询角色信息")
    @ApiOperation(value = "根据角色编码查询角色信息", notes = "根据角色编码查询角色信息", httpMethod = "GET")
    @GetMapping("/queryRolesByRoleCodes/{roleCodes}/{appId}")
    public List<SysRoleVo> queryRolesByRoleCodes(@PathVariable("roleCodes") String roleCodes,@PathVariable("appId") String appId){
    	return sysRoleService.queryRolesByRoleCodes(appId,roleCodes);
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据角色编码查询剩余角色信息")
    @ApiOperation(value = "根据角色编码查询剩余角色信息", notes = "根据角色编码查询剩余角色信息", httpMethod = "GET")
    @GetMapping("/queryExistRolesByRoleCodes/{roleCodes}/{appId}")
    public List<SysRoleVo> queryExistRolesByRoleCodes(@PathVariable("roleCodes") String roleCodes,@PathVariable("appId") String appId){
    	return sysRoleService.queryExistRolesByRoleCodes(appId,roleCodes);
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询所有角色信息")
    @ApiOperation(value = "查询所有角色信息", notes = "查询所有角色信息", httpMethod = "GET")
    @GetMapping("/queryRoleVos/{appId}")
    public List<SysRoleVo> queryRoleVos(@PathVariable("appId") String appId){
    	return sysRoleService.queryRoleVos(appId);
    }
    
}
