package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:14:21
 */
@Data
public class SysWorkflowNodeInfoQuery extends Page<SysWorkflowNodeInfo> {

	/**
	 * 节点ID
	 */
	private String wfnodeId;
	/**
	 * 节点名称
	 */
	private String wfnodeName;
	/**
	 * 上级节点ID
	 */
	private String parentIds;
	/**
	 * 工作流ID
	 */
	private String workflowId;
	/**
	 * 节点说明
	 */
	private String wfnodeRmk;
	/**
	 * 通过人数 0-所有人通过、N-n个人通过
	 */
	private Integer agreeNum;
	/**
	 * 不通过人数 0-所有人不通过、N-n个人不通过
	 */
	private Integer disagreeNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date modifyTime;
	/**
	 * 节点层级
	 */
	private int wfnodeLevel;
}
