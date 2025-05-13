package com.adtec.rdc.base.workflow.feign.bo;

import java.util.Map;

import lombok.Data;

@Data
public class WorkflowCreate {
	/**
	 * 工作流标识
	 */
	private String workflowCode;
	/**
	 * 实例名称
	 */
	private String instanceName;
	/**
	 * 工作流版本
	 */
	private String versionNum;
	/**
	 * 应用ID
	 */
	private String appId;
	/**
	 * 当前用户
	 */
	private String userId;
	/**
	 * 宏变量map
	 */
	private Map<String, Object> macroMap;
}
