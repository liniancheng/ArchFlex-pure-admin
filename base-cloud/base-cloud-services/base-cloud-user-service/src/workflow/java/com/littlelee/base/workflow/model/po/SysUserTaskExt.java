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
 * 用户任务扩展表
 * </p>
 *
 * @author littlelee
 * @date 2020-07-20 19:19:57
 */
@Data
@Accessors(chain = true)
public class SysUserTaskExt implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 任务扩展ID
	 */
	@TableId(value = "EXT_ID", type = IdType.ASSIGN_UUID)
	private String extId;

	/**
	 * 任务扩展名称
	 */
	@TableField(value = "EXT_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String extName;
	/**
	 * 待处理查询SQL
	 */
	@TableField(value = "UNDO_SQL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String undoSql;
	/**
	 * 已处理查询SQL
	 */
	@TableField(value = "DONE_SQL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String doneSql;
	/**
	 * 任务URL
	 */
	@TableField(value = "EXT_URL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String extUrl;
	/**
	 * 任务扩展描述
	 */
	@TableField(value = "EXT_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String extRmk;
	/**
	 * 租户ID
	 */
	@TableField(value = "APP_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String appId;
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

}