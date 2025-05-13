package com.adtec.rdc.base.migrate.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.migrate.model.bo.SysMigrateTree;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;
import com.adtec.rdc.base.migrate.model.query.SysMigrateInfoQuery;
import com.adtec.rdc.base.migrate.service.SysMigrateInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@RestController
@RequestMapping("/migrate")
@Api(value = "导入导出信息表controller", tags = {"导入导出信息表操作接口"})
public class SysMigrateInfoController {
	private static final String FUNC_NAME = "导入导出信息表功能";
	
    @Autowired
    private SysMigrateInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加导入导出信息表")
    @ApiOperation(value = "添加导入导出信息表", notes = "导入导出信息表信息", httpMethod = "POST")
    @ApiImplicitParam(name = "migrate", value = "导入导出信息表信息", required = true, dataType = "SysMigrateInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysMigrateInfo migrate){
        return new ApiResult<>(service.onlySave(migrate));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改导入导出信息表")
    @ApiOperation(value = "修改导入导出信息表", notes = "导入导出信息表信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "migrate", value = "导入导出信息表信息", required = true, dataType = "SysMigrateInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysMigrateInfo migrate){
        return new ApiResult<>(service.onlyUpdateById(migrate));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除导入导出信息表")
    @ApiOperation(value = "删除导入导出信息表", notes = "删除导入导出信息表信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "导入导出信息表id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询导入导出信息表信息")
    @ApiOperation(value = "查询导入导出信息表信息", notes = "通过主键查询导入导出信息表信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "导入导出信息表id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysMigrateInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "导入导出信息表信息分页查询", notes = "导入导出信息表信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "导入导出信息表信息查询类", required = false, dataType = "SysMigrateInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysMigrateInfoQuery> pageByQuery(SysMigrateInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    //////////////////////////////////////////功能改造/////////////////////////////////////////////////////
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "按pId加载资源节点")
    @GetMapping("/getTreeNodes/{pId}")
    @ApiOperation(value = "按pId加载资源节点", notes = "", httpMethod = "GET")
    @ApiImplicitParam(name = "pId", value = "父节点id", required = true, dataType = "string")
    public ApiResult<List<SysMigrateTree>> getTreeNodes(@PathVariable("pId") String pId){
    	return new ApiResult<List<SysMigrateTree>>(service.getTreeNodes(pId));
    }
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "获取资源树全部节点")
    @GetMapping("/getAllTreeNodes")
    @ApiOperation(value = "获取资源树全部节点", notes = "", httpMethod = "GET")
    public ApiResult<List<SysMigrateTree>> getAllTreeNodes(){
    	return new ApiResult<List<SysMigrateTree>>(service.getAllTreeNodes());
    }
    
}
