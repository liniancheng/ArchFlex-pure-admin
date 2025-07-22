package com.littlelee.base.user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.littlelee.base.common.model.vo.SysBranchInfoVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.littlelee.base.common.annotation.SysLog;
import com.littlelee.base.common.constants.ServiceNameConstants;
import com.littlelee.base.common.exception.ServiceException;
import com.littlelee.base.common.model.bo.TreeNode;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.user.model.bo.SysBranchTree;
import com.littlelee.base.user.model.po.SysBranchInfo;
import com.littlelee.base.user.model.query.SysBranchQuery;
import com.littlelee.base.user.service.SysBranchInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author: liushp
 * @date: 2019/11/27
 * @description: 机构管理
 */
@RestController
@RequestMapping("/branch")
@Tag(name = "机构操作接口", description = "机构 controller")
public class SysBranchInfoController {

	private static final String MODULE_NAME = "系统机构模块";

	@Autowired
	private SysBranchInfoService service;

	@GetMapping("/fetchBranchs")
	@Operation(summary = "获取所有的一级机构", description = "获取所有的一级机构")
	public ApiResult<List<SysBranchInfo>> fetchBranchs() {
		return new ApiResult<>(service.fetchBranchs());
	}

	@GetMapping("/tree")
	@Operation(summary = "获取所有机构的树", description = "获取所有机构的树")
	public ApiResult<List<SysBranchTree>> getAllBranchTree() {
		return new ApiResult<>(service.getAllBranchTree());
	}

	@GetMapping("/treeNode")
	@Operation(summary = "获取所有机构的树（节点形式）", description = "获取所有机构的树")
	public ApiResult<List<TreeNode>> getAllBranchTreeNode() {
		return new ApiResult<>(service.getAllBranchTreeNode());
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "添加机构信息")
	@Operation(summary = "添加机构信息", description = "添加机构信息")
	@PostMapping
	public ApiResult<Boolean> saveBranch(
			@Parameter(description = "机构信息", required = true)
			@RequestBody SysBranchInfo branch) {
		return new ApiResult<>(service.save(branch));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "修改机构信息")
	@Operation(summary = "修改机构信息", description = "修改机构信息")
	@PutMapping
	public ApiResult<Boolean> updateBranch(
			@Parameter(description = "机构信息", required = true)
			@RequestBody SysBranchInfo branch) {
		return new ApiResult<>(service.updateById(branch));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "根据id查询机构信息")
	@Operation(summary = "查询机构信息", description = "根据id查询机构信息")
	@GetMapping("/id/{id}")
	public ApiResult<SysBranchInfo> getById(
			@Parameter(description = "机构id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.getById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "根据id删除机构信息")
	@Operation(summary = "删除机构信息", description = "根据id删除机构信息")
	@DeleteMapping("/id/{id}")
	public ApiResult<Boolean> deleteBranch(
			@Parameter(description = "机构id", required = true)
			@PathVariable("id") String id) {
		return new ApiResult<>(service.removeById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "机构导入模板下载")
	@Operation(summary = "机构导入模板下载", description = "机构导入模板下载")
	@PostMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		try (InputStream input = new ClassPathResource("download/adminExcelModel/branchModel.xlsx").getInputStream()) {
			byte[] data = IOUtils.toByteArray(input);
			response.reset();
			response.setHeader("Content-Disposition", "attachment;");
			response.addHeader("Content-Length", String.valueOf(data.length));
			response.setContentType("application/octet-stream; charset=UTF-8");
			IOUtils.write(data, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE,
			moduleName = MODULE_NAME, actionName = "批量导入机构信息")
	@Operation(summary = "批量导入机构信息", description = "批量导入机构信息")
	@PostMapping("/upload")
	public ApiResult<String> uploadBranchs(
			@Parameter(description = "导入的Excel文件", required = true)
			@RequestParam("file") MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			String importSize = service.importBranch(inputStream);
			return new ApiResult<>(importSize);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@GetMapping("/tree/{type}")
	@Operation(summary = "获取指定类型机构树", description = "获取指定类型机构树")
	public ApiResult<List<TreeNode>> treeBranchNosByType(
			@Parameter(description = "机构类型", required = true)
			@PathVariable("type") String type) {
		return new ApiResult<>(service.treeBranchNosByType(type));
	}

	@GetMapping("/treeByPage")
	@Operation(summary = "获取机构树分页", description = "获取机构树分页")
	public ApiResult<SysBranchQuery> getAllBranchTree(
			@Parameter(description = "查询条件", required = false)
			SysBranchQuery query) {
		return new ApiResult<>(service.getAllBranchTreeByPage(query));
	}

	@PostMapping("/selectBatchByIds")
	@Operation(summary = "根据ID批量查询机构", description = "根据ID批量查询机构")
	public List<SysBranchInfoVo> selectBatchByIds(
			@Parameter(description = "机构ID列表", required = true)
			@RequestBody List<String> orgVals) {
		return service.selectBatchByIds(orgVals);
	}

	@GetMapping("/selectById/{orgVal}")
	@Operation(summary = "根据ID查询单个机构", description = "根据ID查询单个机构")
	public SysBranchInfoVo selectById(
			@Parameter(description = "机构ID", required = true)
			@PathVariable("orgVal") String orgVal) {
		SysBranchInfo sysBranchInfo = service.getById(orgVal);
		SysBranchInfoVo vo = new SysBranchInfoVo();
		if (sysBranchInfo != null) {
			BeanUtils.copyProperties(sysBranchInfo, vo);
		}
		return vo;
	}
}