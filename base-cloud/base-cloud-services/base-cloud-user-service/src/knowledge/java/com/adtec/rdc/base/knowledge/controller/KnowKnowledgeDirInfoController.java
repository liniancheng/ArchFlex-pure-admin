package com.adtec.rdc.base.knowledge.controller;

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

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeDirInfoTree;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeDirInfoQuery;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeDirInfoService;
import com.adtec.rdc.base.user.model.bo.SysMenuTree;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@RestController
@RequestMapping("/dir")
@Api(value = "知识库类型controller", tags = {"知识库类型操作接口"})
public class KnowKnowledgeDirInfoController {
	private static final String FUNC_NAME = "知识库类型功能";
	@Autowired
	private HttpServletRequest request;
    @Autowired
    private KnowKnowledgeDirInfoService service;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加知识库类型")
    @ApiOperation(value = "添加知识库类型", notes = "知识库类型信息", httpMethod = "POST")
    @ApiImplicitParam(name = "dir", value = "知识库类型信息", required = true, dataType = "KnowKnowledgeDirInfo")
    @PostMapping
    public ApiResult<Boolean> save(@RequestBody KnowKnowledgeDirInfo dir){
    	dir.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.save(dir));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改知识库类型")
    @ApiOperation(value = "修改知识库类型", notes = "知识库类型信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "dir", value = "知识库类型信息", required = true, dataType = "KnowKnowledgeDirInfo")
    @PutMapping
    public ApiResult<Boolean> update(@RequestBody KnowKnowledgeDirInfo dir){
    	dir.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.updateById(dir));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除知识库类型")
    @ApiOperation(value = "删除知识库类型", notes = "删除知识库类型信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "知识库类型id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询知识库类型信息")
    @ApiOperation(value = "查询知识库类型信息", notes = "通过主键查询知识库类型信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "知识库类型id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<KnowKnowledgeDirInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "知识库类型信息分页查询", notes = "知识库类型信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "知识库类型信息查询类", required = false, dataType = "KnowKnowledgeDirInfoQuery")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeDirInfoQuery> pageByQuery(KnowKnowledgeDirInfoQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    
    /**
     * 获取所有的类型树
     * @return
     */
    @GetMapping("/tree")
    @ApiOperation(value = "获取所有类型的树", notes = "获取所有类型的树", httpMethod = "GET")
    public ApiResult<List<KnowKnowledgeDirInfoTree>> getAllKnowledgeDirTree(){
        return new ApiResult<>(service.getAllKnowledgeTree());
    }

    /**
     * 获取所有的菜单树
     * @return
     */
    @GetMapping("/treeNode")
    @ApiOperation(value = "获取所有类型的树", notes = "获取所有类型的树", httpMethod = "GET")
    public ApiResult<List<TreeNode>> getAllKnowledgeTreeNode(){
        return new ApiResult<>(service.getAllKnowledgeNode());
    }

}
