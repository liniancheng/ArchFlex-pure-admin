package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:14:51
 */
@Data
public class SysWorkflowInstanceNodeOperQuery extends Page<SysWorkflowInstanceNodeOper> {

	/**
	 * 操作ID
	 */
	private String operId;
	/**
	 * 操作用户
	 */
	private String loginName;
	/**
	 * 是否同意 1-同意 0-不同意
	 */
	private String agreeFlag;
	/**
	 * 处理意见
	 */
	private String operRmk;
	/**
	 * 节点ID
	 */
	private String inodeId;
	/**
	 * 实例ID
	 */
	private String instanceId;
	/**
	 * 创建时间
	 */
	private Date operTime;

}
