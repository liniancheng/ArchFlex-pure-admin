package com.adtec.rdc.base.anno.controller;

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

import com.adtec.rdc.base.anno.model.po.SysAnnoAttachInfo;
import com.adtec.rdc.base.anno.service.SysAnnoAttachInfoService;
import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author dengchf
 * @date 2019-12-09 16:56:44
 */
@RestController
@RequestMapping("/annoAttach")
@Api(value = "公告附件信息表controller", tags = { "公告附件信息表操作接口" })
public class SysAnnoAttachInfoController {
	private static final String FUNC_NAME = "公告附件信息表功能";

	@Autowired
	private SysAnnoAttachInfoService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加公告附件信息表")
	@ApiOperation(value = "添加公告附件信息表", notes = "公告附件信息表信息", httpMethod = "POST")
	@ApiImplicitParam(name = "annoAttach", value = "公告附件信息表信息", required = true, dataType = "SysAnnoAttachInfo")
	@PostMapping
	public ApiResult<Boolean> save(@RequestParam("annoId") String annoId, @RequestParam("file")  MultipartFile[] file) {
		return new ApiResult<>(service.onlySave(annoId, file,UserUtil.getAppId(request)));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询公告附件信息表信息")
	@ApiOperation(value = "查询公告附件信息表信息", notes = "通过主键查询公告附件信息表信息", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "公告附件信息表id", required = true, dataType = "string")
	@GetMapping("/{id}")
	public ApiResult<SysAnnoAttachInfo> getById(@PathVariable("id") String id) {
		return new ApiResult<>(service.getById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除公告附件信息表")
	@ApiOperation(value = "删除公告附件信息表", notes = "删除公告附件信息表信息", httpMethod = "DELETE")
	@ApiImplicitParam(name = "id", value = "公告附件信息表id", required = true, dataType = "string")
	@DeleteMapping("/{id}")
	public ApiResult<Boolean> delete(@PathVariable("id") String id) {
		return new ApiResult<>(service.removeById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "下载公告附件")
	@ApiOperation(value = "下载公告附件", notes = "下载公告附件", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "公告附件信息表id", required = true, dataType = "string")
	@GetMapping("/download/{id}")
	public void download(@PathVariable("id") String id, HttpServletResponse response) throws IOException {
		SysAnnoAttachInfo annoAttach = service.getById(id);
		response.reset();
        response.addHeader("Content-Length", "" + annoAttach.getAttContent().length);
        response.addHeader("Content-Disposition", "attachment;fileName=" + annoAttach.getAttName());
        response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(annoAttach.getAttContent(), response.getOutputStream());

	}

}
