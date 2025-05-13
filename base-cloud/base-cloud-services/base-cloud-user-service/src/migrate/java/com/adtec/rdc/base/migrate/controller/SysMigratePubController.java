package com.adtec.rdc.base.migrate.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.migrate.model.bo.MigrateExport;
import com.adtec.rdc.base.migrate.service.SysMigratePubService;
import com.adtec.rdc.base.migrate.utils.FileUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@RestController
@RequestMapping("/migratePub")
@Api(value = "导入导出操作controller", tags = {"导入导出操作接口"})
public class SysMigratePubController {
	private static final String FUNC_NAME = "导入导出操作功能";
	private static final String MIGRATE_EXPOPRT_DEFAULT_NAME = "export.bak";
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
    @Autowired
    private SysMigratePubService pubService;
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "上传数据文件")
    @ApiOperation(value = "上传数据文件", notes = "上传数据文件", httpMethod = "POST")
    @PostMapping(value = "/upload")
    public ApiResult<Boolean> upload(@RequestParam("file") MultipartFile file){
    	if(file == null ||StringUtils.isEmpty(file.getOriginalFilename())) {
    		return new ApiResult<Boolean>(new ServiceException("文件异常，请检查！"));
    	}
    	try {
    		MigrateExport export = FileUtils.readXmlFile(file);
    		return new ApiResult<Boolean>(pubService.upload(export));
    	}catch(Throwable e){
    		e.printStackTrace();
    		return new ApiResult<Boolean>(new ServiceException(e.getMessage()));
    	}
	}
    
    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "下载数据文件")
    @ApiOperation(value = "下载数据文件", notes = "下载数据文件", httpMethod = "POST")
    @PostMapping(value = "/download")
    public void dowload(@RequestParam("ids") List<String> ids) {
    	MigrateExport export = pubService.downLoad(ids);
    	FileUtils.downloadXml(MIGRATE_EXPOPRT_DEFAULT_NAME, export, request, response);
    }

    @SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "获取资源树全部节点")
    @GetMapping("/getAllTreeNodes")
    @ApiOperation(value = "获取资源树全部节点", notes = "", httpMethod = "GET")
    public ApiResult<List<TreeNode>> getAllTreeNodes(){
    	return new ApiResult<List<TreeNode>>(pubService.getAllTreeNodes());
    }
    
}
