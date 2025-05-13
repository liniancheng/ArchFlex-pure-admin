package com.adtec.rdc.base.workflow.feign.bo;

import java.util.List;

import lombok.Data;

/**
 * 工作流节点
 * @author JTao
 *
 */
@Data
public class WorkflowQueryNode {
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点状态
	 */
	private String nodeStatus;
	/**
	 * 节点审批
	 */
	private List<WorkflowQueryNodeOper> opers;
}
