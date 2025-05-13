package com.adtec.rdc.base.resource.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.model.vo.SysDblinkVo;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.resource.model.po.SysDblinkInfo;
import com.adtec.rdc.base.resource.model.query.SysDblinkInfoQuery;
import com.adtec.rdc.base.resource.service.SysDblinkInfoService;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuzhh
 * @date 2019-11-22 14:07:28
 */
@RestController
@RequestMapping("/dblink")
@Api(value = "数据源管理controller", tags = {"数据源管理操作接口"})
public class SysDblinkInfoController {
	private static final String MODULE_NAME = "数据源管理模块";

    @Autowired
    private SysDblinkInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加数据源管理")
    @ApiOperation(value = "添加数据源管理", notes = "数据源管理信息", httpMethod = "POST")
    @ApiImplicitParam(name = "resource", value = "数据源管理信息", required = true, dataType = "SysDblinkInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysDblinkInfo dblinkInfo){
    	try {
    		return new ApiResult<>(service.save(dblinkInfo));
    	}catch(ServiceException e) {
    		return new ApiResult<>().failed(e);
    	}
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改数据源管理")
    @ApiOperation(value = "修改数据源管理", notes = "数据源管理信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "resource", value = "数据源管理信息", required = true, dataType = "SysDblinkInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysDblinkInfo dblinkInfo){
    	try {
    		return new ApiResult<>(service.updateById(dblinkInfo));
    	}catch(ServiceException e) {
    		return new ApiResult<>().failed(e);
    	}
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除数据源管理")
    @ApiOperation(value = "删除数据源管理", notes = "删除数据源管理信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "数据源管理id", required = true, dataType = "string")
    @DeleteMapping("/delete/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
    	try {
    		return new ApiResult<>(service.removeById(id));
    	}catch(ServiceException e) {
    		return new ApiResult<>().failed(e);
    	}
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询数据源管理信息以及相关联的资源信息")
    @ApiOperation(value = "查询数据源管理信息", notes = "查询数据源管理信息以及相关联的资源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "数据源管理id", required = true, dataType = "string")
    @GetMapping("/getById/{id}")
    public ApiResult<SysDblinkInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "数据源管理信息分页查询", notes = "数据源管理信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "数据源管理信息查询类", required = false, dataType = "SysDblinkInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysDblinkInfoQuery> pageByQuery(SysDblinkInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "数据源测试")
    @ApiOperation(value = "数据源测试", notes = "数据源测试", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "数据源id", required = true, dataType = "string")
    @GetMapping("/test/{id}")
    public ApiResult<Boolean> test(@PathVariable("id") String id){
    	try {
    		return new ApiResult<>(service.test(id));
    	}catch(ServiceException e) {
    		return new ApiResult<>().failed(e);
    	}
    }
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "可用数据源列表")
    @ApiOperation(value = "可用数据源列表", notes = "可用数据源列表", httpMethod = "GET")
    @GetMapping("/list")
    public ApiResult<List<TreeNode>> list(){
    	try {
    		return new ApiResult<>(service.listDb());
    	}catch(ServiceException e) {
    		return new ApiResult<>().failed(e);
    	}
    }
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "可用数据源列表")
    @ApiOperation(value = "可用数据源列表", notes = "可用数据源列表", httpMethod = "GET")
    @GetMapping("/listDbVo")
    public List<SysDblinkVo> listDbVo(){
    	return service.listDbVo();
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "查询数据源信息")
    @ApiOperation(value = "查询数据源管理信息", notes = "查询数据源信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "数据源管理id", required = true, dataType = "string")
    @GetMapping("/getVoById/{id}")
    public SysDblinkVo getVoById(@PathVariable("id") String id){
        return service.getVoById(id);
    }
}
