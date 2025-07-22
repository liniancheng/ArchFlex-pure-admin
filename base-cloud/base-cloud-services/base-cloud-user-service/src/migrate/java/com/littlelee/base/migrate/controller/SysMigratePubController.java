package com.littlelee.base.migrate.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.migrate.model.bo.MigrateExport;
import com.littlelee.base.migrate.service.SysMigratePubService;
import com.littlelee.base.migrate.utils.FileUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author dengchf
 * @date 2019-12-15 22:22:24
 */
@RestController
@RequestMapping("/migratePub")
@Tag(name = "导入导出操作接口", description = "导入导出操作 controller")
public class SysMigratePubController {

	private static final String FUNC_NAME = "导入导出操作功能";
	private static final String MIGRATE_EXPORT_DEFAULT_NAME = "export.bak";

	@Autowired
	private HttpServletRequest request;
	@Autowired
	private HttpServletResponse response;
	@Autowired
	private SysMigratePubService pubService;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "上传数据文件")
	@Operation(summary = "上传数据文件", description = "上传数据文件")
	@PostMapping("/upload")
	public ApiResult<Boolean> upload(@Parameter(description = "上传的 XML 文件", required = true) @RequestParam("file") MultipartFile file) {
		if (file == null || StringUtils.isEmpty(file.getOriginalFilename())) {
			return new ApiResult<>(new ServiceException("文件异常，请检查！"));
		}
		try {
			MigrateExport export = FileUtils.readXmlFile(file);
			return new ApiResult<>(pubService.upload(export));
		} catch (Throwable e) {
			return new ApiResult<>(new ServiceException(e.getMessage()));
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "下载数据文件")
	@Operation(summary = "下载数据文件", description = "根据节点 ID 列表导出数据文件")
	@PostMapping("/download")
	public void download(@Parameter(description = "节点 ID 列表", required = true) @RequestParam("ids") List<String> ids) {
		MigrateExport export = pubService.downLoad(ids);
		FileUtils.downloadXml(MIGRATE_EXPORT_DEFAULT_NAME, export, request, response);
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "获取资源树全部节点")
	@Operation(summary = "获取资源树全部节点", description = "获取完整的资源树节点列表")
	@GetMapping("/getAllTreeNodes")
	public ApiResult<List<TreeNode>> getAllTreeNodes() {
		return new ApiResult<>(pubService.getAllTreeNodes());
	}
}