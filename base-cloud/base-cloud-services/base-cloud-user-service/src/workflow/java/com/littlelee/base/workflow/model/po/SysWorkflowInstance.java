package com.littlelee.base.workflow.model.po;

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
 * @author littlelee
 * @date 2020-06-30 09:15:15
 */
@Data
@Accessors(chain = true)
public class SysWorkflowInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 实例ID
	 */
	@TableId(value = "INSTANCE_ID", type = IdType.ASSIGN_UUID)
	private String instanceId;

	/**
	 * 工作流ID
	 */
	@TableField(value = "WORKFLOW_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String workflowId;
	/**
	 * 实例名称
	 */
	@TableField(value = "INSTANCE_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String instanceName;
	/**
	 * 实例说明
	 */
	@TableField(value = "INSTANCE_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String instanceRmk;
	/**
	 * 实例状态 0-未处理 1-处理中 2-已完成
	 */
	@TableField(value = "INSTANCE_STATUS", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String instanceStatus;
	/**
	 * 创建用户
	 */
	@TableField(value = "CREATE_USER", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String createUser;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private LocalDateTime modifyTime;

	/**
	 * 租户ID
	 */
	@TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String appId;
	
}