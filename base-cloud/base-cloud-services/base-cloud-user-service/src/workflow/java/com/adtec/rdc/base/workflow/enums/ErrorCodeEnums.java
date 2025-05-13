package com.adtec.rdc.base.workflow.enums;

public enum ErrorCodeEnums {
	//	19-->workflow type：工作流分类 </br>
	WORKFLOW_TYPE_RE_NAME(1000031905, "分类名称已存在！"), 
	WORKFLOW_TYPE_HAS_WORKFLOW(1000031906,"分类下存在工作流！"),
	//	20-->workflow：工作流 </br>
	WORKFLOW_RE_NAME(1000032005,"工作流名称已存在！"),
	WORKFLOW_RE_CODE(1000032005,"工作流标识已存在！"),
	WORKFLOW_HAS_INSTANCE(1000032006,"工作流下存在流程实例！"),
	//	21-->workflow node：工作流节点 </br>
	WORKFLOW_NODE_RE_NAME(1000032105,"节点名称已存在！"),
	WORKFLOW_NODE_HAS_CHILD(1000032106,"节点下存在子节点！"),
	//	22-->workflow node auth：工作流节点权限 </br>
	WORKFLOW_NODE_AUTH_NOT_EXIST_USER(1000032201, "用户不存在"),
	WORKFLOW_NODE_OPER_NOT_RIGHT_USER(1000032202,"非法审批用户！"),
	WORKFLOW_NODE_OPER_NOT_EXIST_INSTANCE(1000032202,"工作流程不存在！"),
	WORKFLOW_NODE_OPER_INSTANCE_FINISHED(1000032202,"工作流程已结束！"),
	WORKFLOW_NODE_OPER_NOT_EXIST_INSTANCE_NODE(1000032202,"工作流程节点不存在！"),
	WORKFLOW_NODE_OPER_INSTANCE_NODE_FINISHED(1000032202,"工作流程节点已结束！"),
	WORKFLOW_NODE_OPER_CALLBACK_ERROR(1000032202,"工作流程回调更新状态失败！"),
	WORKFLOW_NODE_OPER_EXIST_USER_OPER(1000032202,"用户已经审批！"),
	//  23-->workflow instance：工作流实例 </br>
	WORKFLOW_INSTANCE_NOTEXIST(1000032001,"流程不存在！"),
	WORKFLOW_INSTANCE_CODE_EMPTY(1000032001,"流程标识为空！"),
	WORKFLOW_INSTANCE_CODE_NOTEXIST(1000032001,"流程标识不存在！"),
	WORKFLOW_INSTANCE_VERSION_NOTEXIST(1000032001,"流程版本不存在！"),
	WORKFLOW_INSTANCE_NOT_NODE(1000032301, "流程没有定义节点"),
	// 24-->workflow macro：工作流宏变量</br>
	WORKFLOW_MACRO_RE_NAME(1000032405,"宏变量名称已存在！"),
	WORKFLOW_MACRO_RE_CODE(1000032405,"宏变量标识已存在！"),
	//	25-->user task ext：自定义任务 </br>
	USER_TASK_EXT_RE_NAME(1000032505,"自定义任务已存在！"),
	;
	
	private Integer errorCode;
	private String message;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	ErrorCodeEnums(Integer errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
}
