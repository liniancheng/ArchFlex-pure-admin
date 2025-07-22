package com.littlelee.base.workflow.feign.bo;

import lombok.Data;

/**
 * 节点审批
 * @author littlelee
 *
 */
@Data
public class WorkflowQueryNodeOper {
	/**
	 * 审批时间
	 */
	private String operTime;
	/**
	 * 审批状态
	 */
	private String operStatus;
	/**
	 * 审批用户登录名
	 */
	private String loginName;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 审批说明
	 */
	private String operRmk;
}
