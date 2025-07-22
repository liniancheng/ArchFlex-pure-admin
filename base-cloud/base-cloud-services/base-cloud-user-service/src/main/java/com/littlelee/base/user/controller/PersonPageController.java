package com.littlelee.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.littlelee.base.anno.model.query.SysAnnoInfoQuery;
import com.littlelee.base.anno.service.SysAnnoInfoService;
import com.littlelee.base.common.base.controller.BaseHttpController;
import com.littlelee.base.common.util.ApiResult;
import com.littlelee.base.common.util.UserUtil;
import com.littlelee.base.user.model.query.SysMessageInfoQuery;
import com.littlelee.base.user.service.PersonPageService;
import com.littlelee.base.user.service.SysMessageInfoService;
import com.littlelee.base.workflow.model.bo.Workflow;
import com.littlelee.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.littlelee.base.workflow.model.query.WorkflowInfoQuery;
import com.littlelee.base.workflow.service.WorkflowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 个人中心
 * @author littlelee
 */
@RestController
@RequestMapping("/base/person")
@Tag(name = "个人中心信息获取接口", description = "个人中心 controller")
public class PersonPageController extends BaseHttpController {

	@Autowired
	private PersonPageService service;
	@Autowired
	private SysMessageInfoService messageService;
	@Autowired
	private SysAnnoInfoService annoService;
	@Autowired
	private WorkflowService workflowService;

	@Operation(summary = "消息&公告分页查询", description = "消息&公告分页查询")
	@GetMapping("/allMessages")
	public ApiResult<SysMessageInfoQuery> allMessages(
			@Parameter(description = "消息信息查询类", required = false)
			SysMessageInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setMessageRevUser(UserUtil.getUserId(request));
		return new ApiResult<>(service.pageByQuery(query));
	}

	@Operation(summary = "消息分页查询", description = "消息分页查询")
	@GetMapping("/messages")
	public ApiResult<SysMessageInfoQuery> messages(
			@Parameter(description = "消息信息查询类", required = false)
			SysMessageInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setMessageRevUser(UserUtil.getUserId(request));
		return new ApiResult<>(messageService.pageByQuery(query));
	}

	@Operation(summary = "公告信息分页查询", description = "公告信息分页查询")
	@GetMapping("/annos")
	public ApiResult<SysAnnoInfoQuery> annos(
			@Parameter(description = "公告信息查询类", required = false)
			SysAnnoInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setUserId(UserUtil.getUserId(request));
		query.setIsValid(1);
		return new ApiResult<>(annoService.appPageByQuery(query));
	}

	@Operation(summary = "标记消息已读", description = "标记消息已读")
	@Parameters({
			@Parameter(name = "type", description = "消息类型", required = true),
			@Parameter(name = "id", description = "消息id", required = true)
	})
	@PostMapping("/readMessage/{type}/{id}")
	public ApiResult<Boolean> readMessage(
			@PathVariable("type") String type,
			@PathVariable("id") String id) {
		return new ApiResult<>(service.readMessage(type, id, UserUtil.getUserId(request)));
	}

	@Operation(summary = "标记消息删除", description = "标记消息删除")
	@Parameters({
			@Parameter(name = "type", description = "消息类型", required = true),
			@Parameter(name = "id", description = "消息id", required = true)
	})
	@PostMapping("/deleteMessage/{type}/{id}")
	public ApiResult<Boolean> deleteMessage(
			@PathVariable("type") String type,
			@PathVariable("id") String id) {
		return new ApiResult<>(service.deleteMessage(type, id, UserUtil.getUserId(request)));
	}

	@Operation(summary = "任务信息分页查询", description = "任务信息分页查询")
	@GetMapping("/tasks")
	public ApiResult<WorkflowInfoQuery> tasks(
			@Parameter(description = "任务信息查询类", required = false)
			WorkflowInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setUserId(UserUtil.getUserId(request));
		query.setLoginName(UserUtil.getLoginName(request));
		query.setRoleIds(UserUtil.getRoleIds(request));
		return new ApiResult<>(workflowService.pageByQuery(query));
	}

	@Operation(summary = "用户任务审批", description = "用户任务审批")
	@PostMapping("/operTask")
	public ApiResult<SysWorkflowInstanceNodeOper> operTask(
			@Parameter(description = "账户信息", required = true)
			@RequestBody Workflow workflow) {
		return new ApiResult<>(workflowService.update(workflow, UserUtil.getUserId(request), UserUtil.getLoginName(request)));
	}
}