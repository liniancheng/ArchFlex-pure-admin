package com.adtec.rdc.base.workflow.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 工作流实例节点审批
 * </p>
 *
 * @author adtec
 * @date 2020-06-30 09:14:51
 */
@Data
@Accessors(chain = true)
@TableName(value = "SYS_WORKFLOW_INST_NODE_OPER")
public class SysWorkflowInstanceNodeOper implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 操作ID
	 */
	@TableId(value = "OPER_ID", type = IdType.UUID)
	private String operId;

	/**
	 * 操作用户
	 */
	@TableField(value = "LOGIN_NAME", strategy = FieldStrategy.IGNORED)
	private String loginName;
	/**
	 * 是否同意 1-同意 0-不同意
	 */
	@TableField(value = "AGREE_FLAG", strategy = FieldStrategy.IGNORED)
	private String agreeFlag;
	/**
	 * 处理意见
	 */
	@TableField(value = "OPER_RMK", strategy = FieldStrategy.IGNORED)
	private String operRmk;
	/**
	 * 节点ID
	 */
	@TableField(value = "INODE_ID", strategy = FieldStrategy.IGNORED)
	private String inodeId;
	/**
	 * 实例ID
	 */
	@TableField(value = "INSTANCE_ID", strategy = FieldStrategy.IGNORED)
	private String instanceId;
	/**
	 * 创建时间
	 */
	@TableField(value = "OPER_TIME", strategy = FieldStrategy.IGNORED)
	private Date operTime;

	@TableField(exist = false)
	private String userName;

}