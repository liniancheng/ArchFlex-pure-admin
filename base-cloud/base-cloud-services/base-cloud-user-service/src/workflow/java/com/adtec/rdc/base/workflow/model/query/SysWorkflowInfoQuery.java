package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:15:26
 */
@Data
public class SysWorkflowInfoQuery extends Page<SysWorkflowInfo> {

	/**
	 * 工作流ID
	 */
	private String workflowId;

	/**
	 * 工作流标识
	 */
	private String workflowCode;
	/**
	 * 版本号
	 */
	private String versionNum;
	/**
	 * 工作流名称
	 */
	private String workflowName;
	/**
	 * 工作流描述
	 */
	private String workflowRmk;
	/**
	 * 工作流级别
	 */
	private Integer workflowLevel;
	/**
	 * 工作流类型ID
	 */
	private String typeId;
	/**
	 * 流程平台ID
	 */
	private String wfId;
	/**
	 * 租户ID
	 */
	private String appId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date modifyTime;

}
