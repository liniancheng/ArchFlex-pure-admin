package com.adtec.rdc.base.resource.controller;

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
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.resource.model.po.NoticeSmsTempInfo;
import com.adtec.rdc.base.resource.model.query.NoticeSmsTempInfoQuery;
import com.adtec.rdc.base.resource.service.NoticeSmsTempInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-16 13:02:03
 */
@RestController
@RequestMapping("/smstemp")
@Api(value = "smstempcontroller", tags = {"短信模板操作接口"})
public class NoticeSmsTempInfoController {
	private static final String FUNC_NAME = "短信模板功能";
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private NoticeSmsTempInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加短信模板")
    @ApiOperation(value = "添加短信模板", notes = "短信模板信息", httpMethod = "POST")
    @ApiImplicitParam(name = "smstemp", value = "短信模板信息", required = true, dataType = "NoticeSmsTempInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody NoticeSmsTempInfo smstemp){
    	smstemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(smstemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改短信模板")
    @ApiOperation(value = "修改短信模板", notes = "短信模板信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "smstemp", value = "短信模板信息", required = true, dataType = "NoticeSmsTempInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody NoticeSmsTempInfo smstemp){
    	smstemp.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(smstemp));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除短信模板")
    @ApiOperation(value = "删除短信模板", notes = "删除短信模板信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "短信模板id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询短信模板信息")
    @ApiOperation(value = "查询短信模板信息", notes = "通过主键查询短信模板信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "短信模板id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<NoticeSmsTempInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "短信模板信息分页查询", notes = "短信模板信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "短信模板信息查询类", required = false, dataType = "NoticeSmsTempInfoQuery")
    @GetMapping("/page")
    public ApiResult<NoticeSmsTempInfoQuery> pageByQuery(NoticeSmsTempInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
