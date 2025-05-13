package com.adtec.rdc.base.anno.controller;

import com.adtec.rdc.base.common.annotation.SysLog;
import com.adtec.rdc.base.common.constants.ServiceNameConstants;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.anno.model.po.SysAnnoInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;
import com.adtec.rdc.base.anno.model.query.SysAnnoInfoQuery;
import com.adtec.rdc.base.anno.service.SysAnnoInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

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

/**
 * @author dengchf
 * @date 2019-12-01 20:25:26
 */
@RestController
@RequestMapping("/anno")
@Api(value = "公告信息controller", tags = { "公告信息操作接口" })
public class SysAnnoInfoController {
	private static final String FUNC_NAME = "公告信息功能";

	@Autowired
	private SysAnnoInfoService service;
	@Autowired
	private HttpServletRequest request;

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "添加公告信息")
	@ApiOperation(value = "添加公告信息", notes = "公告信息信息", httpMethod = "POST")
	@ApiImplicitParam(name = "anno", value = "公告信息信息", required = true, dataType = "SysAnnoInfo")
	@PostMapping
	public ApiResult<String> save(@RequestBody SysAnnoInfo anno) {
		anno.setLoginName(UserUtil.getLoginName(request));
		anno.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.onlySave(anno));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "修改公告信息")
	@ApiOperation(value = "修改公告信息", notes = "公告信息信息", httpMethod = "PUT")
	@ApiImplicitParam(name = "anno", value = "公告信息信息", required = true, dataType = "SysAnnoInfo")
	@PutMapping
	public ApiResult<String> update(@RequestBody(required = false) SysAnnoInfo anno) {
		anno.setLoginName(UserUtil.getLoginName(request));
		anno.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.onlyUpdate(anno));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "删除公告信息")
	@ApiOperation(value = "删除公告信息", notes = "删除公告信息信息", httpMethod = "DELETE")
	@ApiImplicitParam(name = "id", value = "公告信息id", required = true, dataType = "string")
	@DeleteMapping("/{id}")
	public ApiResult<Boolean> delete(@PathVariable("id") String id) {
		return new ApiResult<>(service.deleteById(id));
	}

	@SysLog(serviceId = ServiceNameConstants.BASE_CLOUD_USER_SERVICE, moduleName = FUNC_NAME, actionName = "通过主键查询公告信息信息")
	@ApiOperation(value = "查询公告信息信息", notes = "通过主键查询公告信息信息", httpMethod = "GET")
	@ApiImplicitParam(name = "id", value = "公告信息id", required = true, dataType = "string")
	@GetMapping("/{id}")
	public ApiResult<SysAnnoInfo> getById(@PathVariable("id") String id) {
		return new ApiResult<>(service.fineById(id));
	}

	@ApiOperation(value = "公告信息信息分页查询", notes = "公告信息信息分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "公告信息信息查询类", required = false, dataType = "SysAnnoInfoQuery")
	@GetMapping("/page")
	public ApiResult<SysAnnoInfoQuery> pageByQuery(SysAnnoInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		return new ApiResult<>(service.pageByQuery(query));
	}
	
	/**
	 * 租户数据展示
	 */
	@ApiOperation(value = "租户公告展示", notes = "租户公告展示", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "租户公告展示类", required = false, dataType = "SysAnnoInfoQuery")
	@GetMapping("/appPage")
	public ApiResult<SysAnnoInfoQuery> appPageByQuery(SysAnnoInfoQuery query){
		query.setAppId(UserUtil.getAppId(request));
		query.setUserId(UserUtil.getUserId(request));
		return new ApiResult<>(service.appPageByQuery(query));
	}
	
	@ApiOperation(value = "获取附件", notes = "获取附件类", httpMethod = "GET")
	@ApiImplicitParam(name = "annoId", value = "公告Id", required = true, dataType = "string")
	@GetMapping("/getAttachById/{id}")
	public ApiResult<List<SysAnnoAttach>> getAttachById(@PathVariable("id") String annoId){
		return new ApiResult<>(service.getAttachById(annoId));
	}
}
