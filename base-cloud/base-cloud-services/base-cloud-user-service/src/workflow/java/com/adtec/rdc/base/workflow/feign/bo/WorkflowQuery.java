package com.adtec.rdc.base.workflow.feign.bo;

import java.util.List;

import lombok.Data;

/**
 * 工作流查询
 * @author JTao
 *
 */
@Data
public class WorkflowQuery {
	/**
	 * 实例ID
	 */
	private String instanceId;
	/**
	 * 实例状态
	 */
	private String instanceStatus;
	/**
	 * 实例节点
	 */
	private List<WorkflowQueryNode> nodes;
}
