package com.adtec.rdc.base.workflow.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 工作流实例节点权限
 * </p>
 *
 * @author adtec
 * @date 2020-07-02 10:06:37
 */
@Data
@Accessors(chain = true)
@TableName(value = "SYS_WORKFLOW_INST_NODE_AUTH")
public class SysWorkflowInstanceNodeAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	@TableId(value = "AUTH_ID", type = IdType.UUID)
	private String authId;

	/**
	 * 权限类型 role-角色，user-用户
	 */
	@TableField(value = "AUTH_TYPE", strategy = FieldStrategy.IGNORED)
	private String authType;
	/**
	 * 对象ID
	 */
	@TableField(value = "OBJ_ID", strategy = FieldStrategy.IGNORED)
	private String objId;
	/**
	 * 节点ID
	 */
	@TableField(value = "INODE_ID", strategy = FieldStrategy.IGNORED)
	private String inodeId;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;

}