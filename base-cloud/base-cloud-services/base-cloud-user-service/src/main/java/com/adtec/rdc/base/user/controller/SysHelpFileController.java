package com.adtec.rdc.base.user.controller;

import java.io.IOException;

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
import com.adtec.rdc.base.user.model.po.SysHelpFileInfo;
import com.adtec.rdc.base.user.model.query.SysHelpFileQuery;
import com.adtec.rdc.base.user.service.SysHelpFileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/help")
@Api(value = "帮助文档controller", tags = {"帮助文档接口"})
public class SysHelpFileController {
	private static final String FUNC_NAME = "帮助文档";
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private SysHelpFileService service;
	
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除帮助文档")
    @ApiOperation(value = "删除帮助文档", notes = "删除帮助文档信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "帮助文档id", required = true, dataType = "string")
    @DeleteMapping("/{id}")
    public ApiResult<Boolean> delete(@PathVariable("id") String id){
        return new ApiResult<>(service.removeById(id));
    }

    @ApiOperation(value = "查询帮助文档信息", notes = "通过主键查询帮助文档信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "帮助文档id", required = true, dataType = "string")
    @GetMapping("/{id}")
    public ApiResult<SysHelpFileInfo> getById(@PathVariable("id") String id){
        return new ApiResult<>(service.getById(id));
    }

    @ApiOperation(value = "帮助文档信息分页查询", notes = "帮助文档信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "帮助文档信息查询类", required = false, dataType = "SysHelpFileQuery")
    @GetMapping("/page")
    public ApiResult<SysHelpFileQuery> pageByQuery(SysHelpFileQuery query){
    	query.setAppId(UserUtil.getAppId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "下载帮助文档")
	@ApiOperation(value = "帮助文档下载", notes = "帮助文档下载", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "文件id", required = true, dataType = "string")
	@GetMapping("/download/{id}")
	public void download(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
    	SysHelpFileInfo helpFile = service.getById(id);
		response.reset();
        response.addHeader("Content-Length", "" + helpFile.getFileContent().length);
        response.addHeader("Content-Disposition", "attachment;fileName=" + helpFile.getFileName());
        response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(helpFile.getFileContent(), response.getOutputStream());
	}
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "上传帮助文档")
	@ApiOperation(value = "上传帮助文档", notes = "上传帮助文档", httpMethod = "POST")
	@ApiImplicitParam(name = "uploadHelpFile", value = "上传帮助文档")
	@PostMapping
	public ApiResult<String> save(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
		return new ApiResult<>(service.upload(file));
	}
}
