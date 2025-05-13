package com.adtec.rdc.base.knowledge.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeAttachInfo;
import com.adtec.rdc.base.knowledge.model.query.KnowKnowledgeAttachInfoQuery;
import com.adtec.rdc.base.knowledge.service.KnowKnowledgeAttachInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author xinglj
 * @date 2020-06-18 15:33:18
 */
@RestController
@RequestMapping("/att")
@Api(value = "附件下载controller", tags = {"附件下载操作接口"})
public class KnowKnowledgeAttachInfoController {
	private static final String FUNC_NAME = "附件下载功能";

    @Autowired
    private KnowKnowledgeAttachInfoService service;
    @Autowired
	private HttpServletRequest request;

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "知识库附件上传")
    @ApiOperation(value = "知识库附件上传", notes = "知识库附件上传", httpMethod = "POST")
    @ApiImplicitParam(name = "att", value = "知识库附件上传", required = true, dataType = "KnowKnowledgeAttachInfo")
    @PostMapping
    public ApiResult<Boolean>  save(@RequestParam("knowledgeId") String knowledgeId, @RequestParam("file") MultipartFile[] file){
		return new ApiResult<>(service.onlySave(knowledgeId, file,UserUtil.getAppId(request)));
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除附件下载")
    @ApiOperation(value = "删除附件下载", notes = "删除附件下载信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "附件下载id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }


    @ApiOperation(value = "附件下载信息分页查询", notes = "附件下载信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "附件下载信息查询类", required = false, dataType = "KnowKnowledgeAttachInfoQuery")
    @GetMapping("/page")
    public ApiResult<KnowKnowledgeAttachInfoQuery> pageByQuery(KnowKnowledgeAttachInfoQuery query){
        return new ApiResult<>(service.pageByQuery(query));
    }	
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "下载知识库附件")
	@ApiOperation(value = "下载知识库附件", notes = "下载知识库附件", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "知识库附件信息表id", required = true, dataType = "string")
	@GetMapping("/download/{id}")
	public void download(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
    	KnowKnowledgeAttachInfo annoAttach = service.getById(id);
		response.reset();
        response.addHeader("Content-Length", "" + annoAttach.getAttContent().length);
        response.addHeader("Content-Disposition", "attachment;fileName=" + annoAttach.getAttName());
        response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(annoAttach.getAttContent(), response.getOutputStream());
	}
    
    @ApiOperation(value = "知识库附件列表", notes = "知识库附件列表", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "知识库附件列表", required = false, dataType = "KnowKnowledgeAttachInfoQuery")
	@GetMapping("/list/{id}")
	public ApiResult<List<KnowKnowledgeAttachInfo>> list(@PathVariable("id") String id){
        return new ApiResult<>(service.getByKnowledgeId(id));
	}

}
