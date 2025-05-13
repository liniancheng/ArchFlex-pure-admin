package com.adtec.rdc.base.resource.controller;

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

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.resource.model.po.NoticeSystemMessage;
import com.adtec.rdc.base.resource.model.query.NoticeSystemMessageQuery;
import com.adtec.rdc.base.resource.service.NoticeSystemMessageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-15 12:18:10
 */
@RestController
@RequestMapping("/sysmsg")
@Api(value = "系统消息controller", tags = {"系统消息操作接口"})
public class NoticeSystemMessageController {
	private static final String FUNC_NAME = "系统消息功能";

    @Autowired
    private NoticeSystemMessageService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加系统消息")
    @ApiOperation(value = "添加系统消息", notes = "系统消息信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysmsg", value = "系统消息信息", required = true, dataType = "NoticeSystemMessage")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody NoticeSystemMessage sysmsg){
        return new ApiResult<>(service.save(sysmsg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改系统消息")
    @ApiOperation(value = "修改系统消息", notes = "系统消息信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysmsg", value = "系统消息信息", required = true, dataType = "NoticeSystemMessage")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody NoticeSystemMessage sysmsg){
        return new ApiResult<>(service.updateById(sysmsg));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除系统消息")
    @ApiOperation(value = "删除系统消息", notes = "删除系统消息信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "系统消息id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }
    @ApiOperation(value = "批量删除系统消息", notes = "删除系统消息", httpMethod = "POST")
    @ApiImplicitParam(name = "ids", value = "系统消息ids", required = true, dataType = "list")
    @PostMapping("/removeByIds")
    public ApiResult<Boolean> deleteByIds(@RequestBody List<String> ids){
        return new ApiResult<>(service.removeByIds(ids));
    }
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询系统消息信息")
    @ApiOperation(value = "查询系统消息信息", notes = "通过主键查询系统消息信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "系统消息id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<NoticeSystemMessage> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "系统消息信息分页查询", notes = "系统消息信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "系统消息信息查询类", required = false, dataType = "NoticeSystemMessageQuery")
    @GetMapping("/page")
    public ApiResult<NoticeSystemMessageQuery> pageByQuery(NoticeSystemMessageQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
