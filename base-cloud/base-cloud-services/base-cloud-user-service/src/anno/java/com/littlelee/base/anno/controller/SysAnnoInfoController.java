package com.littlelee.base.anno.controller;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.anno.model.po.SysAnnoInfo;
import com.littlelee.base.anno.model.query.SysAnnoAttach;
import com.littlelee.base.anno.model.query.SysAnnoInfoQuery;
import com.littlelee.base.anno.service.SysAnnoInfoService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author littlelee
 * @date 2025-12-01 20:25:26
 */
@RestController
@RequestMapping("/anno")
@Tag(name = "公告信息操作接口", description = "公告信息 controller")
public class SysAnnoInfoController {

	private static final String FUNC_NAME = "公告信息功能";

	@Autowired
	private SysAnnoInfoService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "添加公告信息")
	@Operation(summary = "添加公告信息", description = "公告信息信息")
	@PostMapping
	public ApiResult<String> save(
			@Parameter(description = "公告信息信息", required = true)
			@RequestBody SysAnnoInfo anno) {
		anno.setLoginName(UserUtil.getLoginName(request));
		anno.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.onlySave(anno));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "修改公告信息")
	@Operation(summary = "修改公告信息", description = "公告信息信息")
	@PutMapping
	public ApiResult<String> update(
			@Parameter(description = "公告信息信息", required = true)
			@RequestBody(required = false) SysAnnoInfo anno) {
		anno.setLoginName(UserUtil.getLoginName(request));
		anno.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.onlyUpdate(anno));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "删除公告信息")
	@Operation(summary = "删除公告信息", description = "删除公告信息信息")
	@DeleteMapping("/{id}")
	public ApiResult<Boolean> delete(
			@Parameter(description = "公告信息id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.deleteById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = FUNC_NAME, actionName = "通过主键查询公告信息信息")
	@Operation(summary = "查询公告信息信息", description = "通过主键查询公告信息信息")
	@GetMapping("/{id}")
	public ApiResult<SysAnnoInfo> getById(
			@Parameter(description = "公告信息id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.fineById(id));
	}

	@Operation(summary = "公告信息信息分页查询", description = "公告信息信息分页查询")
	@GetMapping("/page")
	public ApiResult<SysAnnoInfoQuery> pageByQuery(
			@Parameter(description = "公告信息信息查询类", required = false)
			SysAnnoInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.pageByQuery(query));
	}

	/**
	 * 租户数据展示
	 */
	@Operation(summary = "租户公告展示", description = "租户公告展示")
	@GetMapping("/appPage")
	public ApiResult<SysAnnoInfoQuery> appPageByQuery(
			@Parameter(description = "租户公告展示类", required = false)
			SysAnnoInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setUserId(UserUtil.getUserId(request));
		return new ApiResult<>(service.appPageByQuery(query));
	}

	@Operation(summary = "获取附件", description = "获取附件类")
	@GetMapping("/getAttachById/{id}")
	public ApiResult<List<SysAnnoAttach>> getAttachById(
			@Parameter(description = "公告Id", required = true)
			@PathVariable("id") String annoId) {
		return new ApiResult<>(service.getAttachById(annoId));
	}
}
