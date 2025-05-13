package com.adtec.rdc.base.user.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adtec.rdc.base.common.model.vo.SysBranchInfoVo;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;
import com.adtec.rdc.base.user.model.query.SysBranchQuery;
import com.adtec.rdc.base.user.service.SysBranchInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @author: liushp
 * @date: 2019/11/27
 * @description: 机构管理
 */
@RestController
@RequestMapping("/branch")
@Api(value = "机构controller", tags = { "机构操作接口" })
public class SysBranchInfoController {

	private static final String MODULE_NAME = "系统机构模块";

	@Autowired
	private SysBranchInfoService service;

	/**
	 * 获取所有的一级机构
	 *
	 * @return
	 */
	@GetMapping("/fetchBranchs")
	@ApiOperation(value = "获取所有的一级机构", notes = "获取所有的一级机构", httpMethod = "GET")
	public ApiResult<List<SysBranchInfo>> fetchBranchs() {
		return new ApiResult<>(service.fetchBranchs());
	}


	/**
	 * 获取所有的机构树
	 *
	 * @return
	 */
	@GetMapping("/tree")
	@ApiOperation(value = "获取所有机构的树", notes = "获取所有机构的树", httpMethod = "GET")
	public ApiResult<List<SysBranchTree>> getAllBranchTree() {
		return new ApiResult<>(service.getAllBranchTree());
	}

	/**
	 * 获取所有的子机构树
	 *
	 * @return
	 */
	@GetMapping("/treeNode")
	@ApiOperation(value = "获取所有机构的树", notes = "获取所有机构的树", httpMethod = "GET")
	public ApiResult<List<TreeNode>> getAllBranchTreeNode() {
		return new ApiResult<>(service.getAllBranchTreeNode());
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "添加机构信息")
	@ApiOperation(value = "添加机构信息", notes = "添加机构信息", httpMethod = "POST")
	@ApiImplicitParam(name = "branch", value = "机构信息", required = true, dataType = "SysBranchInfo")
	@PostMapping
	public ApiResult<Boolean> saveBranch(@RequestBody SysBranchInfo branch) {
		return new ApiResult<Boolean>(service.save(branch));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改机构信息")
	@ApiOperation(value = "修改机构信息", notes = "修改机构信息", httpMethod = "PUT")
	@ApiImplicitParam(name = "branch", value = "机构信息", required = true, dataType = "SysBranchInfo")
	@PutMapping
	public ApiResult<Boolean> updateBranch(@RequestBody SysBranchInfo branch) {
		return new ApiResult<Boolean>(service.updateById(branch));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据id查询机构信息")
	@ApiOperation(value = "查询机构信息", notes = "根据id查询机构信息", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "机构id", required = true, dataType = "string")
	@GetMapping("/id/{id}")
	public ApiResult<SysBranchInfo> getById(@PathVariable("id") String id) {
		return new ApiResult<>(service.getById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据id删除机构信息")
	@ApiOperation(value = "删除机构信息", notes = "根据id删除机构信息", httpMethod = "DELETE")
	@ApiImplicitParam(name = "id", value = "机构id", required = true, dataType = "string")
	@DeleteMapping("/id/{id}")
	public ApiResult<Boolean> deleteBranch(@PathVariable("id") String id) {
		return new ApiResult<>(service.removeById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "机构导入模板下载")
	@ApiOperation(value = "机构导入模板下载", notes = "机构导入模板下载", httpMethod = "POST")
	@PostMapping("/download")
	public void download(HttpServletRequest request, HttpServletResponse response) {
		try {
			ClassPathResource classPathResource = new ClassPathResource("download/adminExcelModel/branchModel.xlsx");
			InputStream input = classPathResource.getInputStream();
			byte[] data = IOUtils.toByteArray(input);
			response.reset();
			response.setHeader("Content-Disposition", "attachment;");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");
			IOUtils.write(data, response.getOutputStream());
			IOUtils.closeQuietly(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = MODULE_NAME, actionName = "批量导入机构信息")
	@ApiOperation(value = "批量导入机构信息", notes = "批量导入机构信息", httpMethod = "POST")
	@PostMapping("/upload")
	public ApiResult<String> uploadBranchs(@RequestParam("file") MultipartFile file) {
		InputStream inputStream;
		String importSize = null;
		try {
			inputStream = file.getInputStream();
			importSize = service.importBranch(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
		return new ApiResult<>(importSize);
	}

	/**
	 * 获取指定类型机构树
	 *
	 * @return
	 */
	@ApiOperation(value = "获取指定类型机构树", notes = "获取指定类型机构树", httpMethod = "GET")
	@ApiImplicitParam(name = "type", value = "机构类型", required = true, dataType = "string")
	@GetMapping("/tree/{type}")
	public ApiResult<List<TreeNode>> treeBranchNosByType(@PathVariable("type") String type) {
		return new ApiResult<>(service.treeBranchNosByType(type));
	}

	/**
	 * 获取机构树分页
	 *
	 * @return
	 */
	@GetMapping("/treeByPage")
	@ApiOperation(value = "获取机构树分页", notes = "获取机构树分页", httpMethod = "GET")
	public ApiResult<SysBranchQuery> getAllBranchTree(SysBranchQuery query) {
		return new ApiResult<>(service.getAllBranchTreeByPage(query));
	}

	@PostMapping("/selectBatchByIds")
	List<SysBranchInfoVo> selectBatchByIds(@RequestBody List<String> orgVals){
		return service.selectBatchByIds(orgVals);
	}
	@GetMapping("/selectById/{orgVal}")
	public SysBranchInfoVo selectById(@PathVariable(value = "orgVal") String orgVal){
		SysBranchInfo sysBranchInfo = service.getById(orgVal);
		SysBranchInfoVo vo = new SysBranchInfoVo();
		if(sysBranchInfo != null) {
			BeanUtils.copyProperties(sysBranchInfo, vo);
		}
		return vo;
	}
}
