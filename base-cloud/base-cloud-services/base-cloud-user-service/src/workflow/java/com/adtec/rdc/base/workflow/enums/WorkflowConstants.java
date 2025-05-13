package com.adtec.rdc.base.workflow.enums;

public class WorkflowConstants {
	/** 流程实例状态 */
	public static final String INSTANCE_STATUS_DOING = "1";//处理中
	public static final String INSTANCE_STATUS_DONE = "2";//完成
	
	/** 流程实例节点状态 */
	public static final String INSTANCE_NODE_STATUS_UNDO = "0";//未处理
	public static final String INSTANCE_NODE_STATUS_AGREE = "1";//通过
	public static final String INSTANCE_NODE_STATUS_DISAGREE = "2";//未通过
	public static final String INSTANCE_NODE_STATUS_DOING = "3";//部分通过
	
	/** 流程宏变量类型 */
	public static final String WORKFLOW_MACRO_TYPE_WORKFLOW = "workflow";
	public static final String WORKFLOW_MACRO_TYPE_INSTANCE = "instance";
	public static final String WORKFLOW_MACRO_TYPE_NODE = "node";
	public static final String WORKFLOW_MACRO_TYPE_AUTH_ROLE = "auth-role";
	public static final String WORKFLOW_MACRO_TYPE_AUTH_USER = "auth-user";
	
	/** 权限类型 */
	public static final String WORKFLOW_NODE_AUTH_TYPE_ROLE = "role";
	public static final String WORKFLOW_NODE_AUTH_TYPE_USER = "user";
	public static final String WORKFLOW_NODE_AUTH_TYPE_ROLE_MACRO = "role_macro";
	public static final String WORKFLOW_NODE_AUTH_TYPE_USER_MACRO = "user_macro";
	
	/** 流程复制范围 */
	public static final String WORKFLOW_COPY_TYPE_ONLY_WORKFLOW = "0";//只复制流程
	public static final String WORKFLOW_COPY_TYPE_WORKFLOW_AND_NODE = "1";//复制流程+节点
	public static final String WORKFLOW_COPY_TYPE_WORKFLOW_AND_NODE_AND_AUTH = "2";//复制流程+节点+权限
}
