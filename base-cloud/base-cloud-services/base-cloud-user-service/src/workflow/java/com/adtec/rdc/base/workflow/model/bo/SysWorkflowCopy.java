package com.adtec.rdc.base.workflow.model.bo;

import lombok.Data;

@Data
public class SysWorkflowCopy {
	private String workflowId;
	private String versionType;
	private String copyType; // 0-仅复制流程 1-复制流程&节点 2-复制流程&节点&权限
}
