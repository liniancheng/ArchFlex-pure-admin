package com.littlelee.base.anno.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

import com.littlelee.base.anno.model.po.SysAnnoAttachInfo;
import com.littlelee.base.anno.service.SysAnnoAttachInfoService;
import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author littlelee
 * @date 2019-12-09 16:56:44
 */
@RestController
@RequestMapping("/annoAttach")
@Tag(name = "公告附件信息表操作接口", description = "公告附件信息表controller")
public class SysAnnoAttachInfoController {

	private static final String FUNC_NAME = "公告附件信息表功能";

	@Autowired
	private SysAnnoAttachInfoService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME,
			actionName = "添加公告附件信息表")
	@Operation(summary = "添加公告附件信息表", description = "公告附件信息表信息")
	@PostMapping
	public ApiResult<Boolean> save(
			@Parameter(description = "公告ID", required = true)
			@RequestParam("annoId") String annoId,
			@Parameter(description = "附件文件", required = true)
			@RequestParam("file") MultipartFile[] file) {
		return new ApiResult<>(service.onlySave(annoId, file, UserUtil.getAppId(request)));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME,
			actionName = "通过主键查询公告附件信息表信息")
	@Operation(summary = "查询公告附件信息表信息", description = "通过主键查询公告附件信息表信息")
	@GetMapping("/{id}")
	public ApiResult<SysAnnoAttachInfo> getById(
			@Parameter(description = "公告附件信息表id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.getById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME,
			actionName = "删除公告附件信息表")
	@Operation(summary = "删除公告附件信息表", description = "删除公告附件信息表信息")
	@DeleteMapping("/{id}")
	public ApiResult<Boolean> delete(
			@Parameter(description = "公告附件信息表id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.removeById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME,
			actionName = "下载公告附件")
	@Operation(summary = "下载公告附件", description = "下载公告附件")
	@GetMapping("/download/{id}")
	public void download(
			@Parameter(description = "公告附件信息表id", required = true)
			@PathVariable("id") String id,
			HttpServletResponse response) throws IOException {

		SysAnnoAttachInfo annoAttach = service.getById(id);
		response.reset();
		response.addHeader("Content-Length", "" + annoAttach.getAttContent().length);
		response.addHeader("Content-Disposition", "attachment; filename=" + annoAttach.getAttName());
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(annoAttach.getAttContent(), response.getOutputStream());
	}
}