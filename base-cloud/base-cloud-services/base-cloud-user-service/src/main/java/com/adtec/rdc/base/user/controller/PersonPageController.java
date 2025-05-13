package com.adtec.rdc.base.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adtec.rdc.base.anno.model.query.SysAnnoInfoQuery;
import com.adtec.rdc.base.anno.service.SysAnnoInfoService;
import com.adtec.rdc.base.common.base.controller.BaseHttpController;
import com.adtec.rdc.base.common.util.ApiResult;
import com.adtec.rdc.base.common.util.UserUtil;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.adtec.rdc.base.user.service.PersonPageService;
import com.adtec.rdc.base.user.service.SysMessageInfoService;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.WorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.WorkflowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 个人中心
 * @author JTao
 *
 */
@RestController
@RequestMapping("/base/person")
@Api(value = "个人中心controller", tags = { "个人中心信息获取接口" })
public class PersonPageController extends BaseHttpController {
	@Autowired
	private PersonPageService service;
	@Autowired
	private SysMessageInfoService messageService;
	@Autowired
	private SysAnnoInfoService annoService;
	@Autowired
	private WorkflowService workflowService;

	@ApiOperation(value = "消息&公告分页查询", notes = "消息&公告分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "消息信息查询类", required = false, dataType = "SysMessageInfoQuery")
    @GetMapping("/allMessages")
    public ApiResult<SysMessageInfoQuery> allMessages(SysMessageInfoQuery query){
		query.setAppId(UserUtil.getAppId(request));
        query.setMessageRevUser(UserUtil.getUserId(request));
        return new ApiResult<>(service.pageByQuery(query));
    }
	
	@ApiOperation(value = "消息分页查询", notes = "消息分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "消息信息查询类", required = false, dataType = "SysMessageInfoQuery")
    @GetMapping("/messages")
    public ApiResult<SysMessageInfoQuery> messages(SysMessageInfoQuery query){
		query.setAppId(UserUtil.getAppId(request));
        query.setMessageRevUser(UserUtil.getUserId(request));
        return new ApiResult<>(messageService.pageByQuery(query));
    }
	
	@ApiOperation(value = "公告信息分页查询", notes = "公告信息分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "公告信息查询类", required = false, dataType = "SysAnnoInfoQuery")
	@GetMapping("/annos")
	public ApiResult<SysAnnoInfoQuery> annos(SysAnnoInfoQuery query) {
		query.setAppId(UserUtil.getAppId(request));
		query.setUserId(UserUtil.getUserId(request));
		query.setIsValid(1);
		return new ApiResult<>(annoService.appPageByQuery(query));
	}
	
    @ApiOperation(value = "标记消息已读", notes = "标记消息已读", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "type", value = "消息类型", required = true, dataType = "string"),
    	@ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string")
    })
    @PostMapping("/readMessage/{type}/{id}")
    public ApiResult<Boolean> readMessage(@PathVariable("type") String type, @PathVariable("id") String id) {
        return new ApiResult(service.readMessage(type, id, UserUtil.getUserId(request)));
    }
    
    @ApiOperation(value = "标记消息删除", notes = "标记消息删除", httpMethod = "POST")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "type", value = "消息类型", required = true, dataType = "string"),
    	@ApiImplicitParam(name = "id", value = "消息id", required = true, dataType = "string")
    })
    @PostMapping("/deleteMessage/{type}/{id}")
    public ApiResult<Boolean> deleteMessage(@PathVariable("type") String type, @PathVariable("id") String id) {
        return new ApiResult(service.deleteMessage(type, id, UserUtil.getUserId(request)));
    }
    
    @ApiOperation(value = "任务信息分页查询", notes = "任务信息分页查询", httpMethod = "GET")
	@ApiImplicitParam(name = "query", value = "任务信息查询类", required = false, dataType = "WorkflowQuery")
	@GetMapping("/tasks")
	public ApiResult<WorkflowInfoQuery> tasks(WorkflowInfoQuery query) {
    	query.setAppId(UserUtil.getAppId(request));
    	query.setUserId(UserUtil.getUserId(request));
		query.setLoginName(UserUtil.getLoginName(request));
		query.setRoleIds(UserUtil.getRoleIds(request));
    	return new ApiResult<>(workflowService.pageByQuery(query));
	}
    
    @ApiOperation(value = "用户任务审批", notes = "用户任务审批", httpMethod = "POST")
	@ApiImplicitParam(name = "workflow", value = "账户信息", required = true, dataType = "Workflow")
	@PostMapping("/operTask")
	public ApiResult<SysWorkflowInstanceNodeOper> operTask(@RequestBody Workflow workflow) {
		return new ApiResult<>(workflowService.update(workflow, UserUtil.getUserId(request), UserUtil.getLoginName(request)));
	}
}
