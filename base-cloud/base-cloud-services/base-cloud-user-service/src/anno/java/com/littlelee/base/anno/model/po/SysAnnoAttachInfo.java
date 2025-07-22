package com.littlelee.base.anno.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 附件信息表
 * </p>
 *
 * @author littlelee
 * @date 2019-12-09 16:56:44
 */
@Data
@Accessors(chain = true)
public class SysAnnoAttachInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "att_id", type = IdType.ASSIGN_UUID)
	private String attId;
	
	/**
	 * 公告id
	 */
	@TableField(value = "anno_id", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String annoId;
	
	/**
	 * 租户id
	 */
	@TableField(value = "app_id", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String appId;

	/**
	 * 附件名称
	 */
	@TableField(value = "att_name", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String attName;

	/**
	 * 附件内容
	 */
	@TableField(value = "att_content", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private byte[] attContent;
}