package com.adtec.rdc.base.knowledge.controller;

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
import com.adtec.rdc.base.common.base.controller.BaseController;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeInfoQuery;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-17 09:36:02
 */
@RestController
@RequestMapping("/knowledge")
@Api(value = "知识库管理controller", tags = {"知识库管理操作接口"})
public class KnowKnowledgeInfoController  {
	private static final String FUNC_NAME = "知识库管理功能";
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private KnowKnowledgeInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加知识库管理")
    @ApiOperation(value = "添加知识库管理", notes = "知识库管理信息", httpMethod = "POST")
    @ApiImplicitParam(name = "knowledge", value = "知识库管理信息", required = true, dataType = "KnowKnowledgeInfo")
    @PostMapping
    public ApiResult<String> save(@RequestBody KnowKnowledgeInfo knowledge){
    	knowledge.setAppId(UserUtil.getAppId(request));
    	knowledge.setCreateUser(UserUtil.getUserId(request));
        return new ApiResult<>(service.onlySave(knowledge));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改知识库管理")
    @ApiOperation(value = "修改知识库管理", notes = "知识库管理信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "knowledge", value = "知识库管理信息", required = true, dataType = "KnowKnowledgeInfo")
    @PutMapping
    public ApiResult<String> update(@RequestBody KnowKnowledgeInfo knowledge){
    	knowledge.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.onlyUpdate(knowledge));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除知识库管理")
    @ApiOperation(value = "删除知识库管理", notes = "删除知识库管理信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "知识库管理id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.deleteById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询知识库管理信息")
    @ApiOperation(value = "查询知识库管理信息", notes = "通过主键查询知识库管理信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "知识库管理id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<KnowKnowledgeInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.fineById(id));
    }

    @ApiOperation(value = "知识库管理信息分页查询", notes = "知识库管理信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "知识库管理信息查询类", required = false, dataType = "KnowKnowledgeInfoQuery")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeInfoQuery> pageByQuery(KnowKnowledgeInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
}
