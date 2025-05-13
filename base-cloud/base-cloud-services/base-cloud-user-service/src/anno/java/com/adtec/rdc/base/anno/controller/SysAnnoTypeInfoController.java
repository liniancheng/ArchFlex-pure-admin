package com.adtec.rdc.base.anno.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.anno.model.po.SysAnnoTypeInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoTypeInfoQuery;
import com.adtec.rdc.base.anno.service.SysAnnoTypeInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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

/**
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
@RestController
@RequestMapping("/annoType")
@Api(value = "公告类型controller", tags = {"公告类型操作接口"})
public class SysAnnoTypeInfoController {
	private static final String FUNC_NAME = "公告类型功能";

    @Autowired
    private SysAnnoTypeInfoService service;
	@Autowired
	private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加公告类型")
    @ApiOperation(value = "添加公告类型", notes = "公告类型信息", httpMethod = "POST")
    @ApiImplicitParam(name = "annoType", value = "公告类型信息", required = true, dataType = "SysAnnoTypeInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody SysAnnoTypeInfo annoType){
		annoType.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlySave(annoType));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改公告类型")
    @ApiOperation(value = "修改公告类型", notes = "公告类型信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "annoType", value = "公告类型信息", required = true, dataType = "SysAnnoTypeInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody SysAnnoTypeInfo annoType){
    	annoType.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlyUpdate(annoType));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除公告类型")
    @ApiOperation(value = "删除公告类型", notes = "删除公告类型信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "公告类型id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询公告类型信息")
    @ApiOperation(value = "查询公告类型信息", notes = "通过主键查询公告类型信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "公告类型id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysAnnoTypeInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "公告类型信息分页查询", notes = "公告类型信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "公告类型信息查询类", required = false, dataType = "SysAnnoTypeInfoQuery")
    @GetMapping("/page")
    public ApiResult<SysAnnoTypeInfoQuery> pageByQuery(SysAnnoTypeInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    @ApiOperation(value = "公告类型信息查询全部", notes = "公告类型信息全部查询", httpMethod = "GET")
    @ApiImplicitParam(name = "findAll", value = "公告类型信息查询类", required = false, dataType = "SysAnnoTypeInfoQuery")
    @GetMapping("/findAll")
    public ApiResult<List<SysAnnoTypeInfo>> findAll(){
    	return new ApiResult<>(service.findAll(UserUtil.getAppId(request)));
    }
}
