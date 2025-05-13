package com.adtec.rdc.base.workflow.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 工作流实例信息
 * </p>
 *
 * @author adtec
 * @date 2020-06-30 09:15:15
 */
@Data
@Accessors(chain = true)
public class SysWorkflowInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 实例ID
	 */
	@TableId(value = "INSTANCE_ID", type = IdType.UUID)
	private String instanceId;

	/**
	 * 工作流ID
	 */
	@TableField(value = "WORKFLOW_ID", strategy = FieldStrategy.IGNORED)
	private String workflowId;
	/**
	 * 实例名称
	 */
	@TableField(value = "INSTANCE_NAME", strategy = FieldStrategy.IGNORED)
	private String instanceName;
	/**
	 * 实例说明
	 */
	@TableField(value = "INSTANCE_RMK", strategy = FieldStrategy.IGNORED)
	private String instanceRmk;
	/**
	 * 实例状态 0-未处理 1-处理中 2-已完成
	 */
	@TableField(value = "INSTANCE_STATUS", strategy = FieldStrategy.IGNORED)
	private String instanceStatus;
	/**
	 * 创建用户
	 */
	@TableField(value = "CREATE_USER", strategy = FieldStrategy.IGNORED)
	private String createUser;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED)
	private LocalDateTime modifyTime;

	/**
	 * 租户ID
	 */
	@TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED)
	private String appId;
	
}