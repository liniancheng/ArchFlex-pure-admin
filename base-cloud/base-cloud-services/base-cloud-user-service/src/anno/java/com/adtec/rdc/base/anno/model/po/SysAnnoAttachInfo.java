package com.adtec.rdc.base.anno.model.po;

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
 * @author dengchf
 * @date 2019-12-09 16:56:44
 */
@Data
@Accessors(chain = true)
public class SysAnnoAttachInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "att_id", type = IdType.UUID)
	private String attId;
	
	/**
	 * 公告id
	 */
	@TableField(value = "anno_id", strategy = FieldStrategy.IGNORED)
	private String annoId;
	
	/**
	 * 租户id
	 */
	@TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
	private String appId;

	/**
	 * 附件名称
	 */
	@TableField(value = "att_name", strategy = FieldStrategy.IGNORED)
	private String attName;

	/**
	 * 附件内容
	 */
	@TableField(value = "att_content", strategy = FieldStrategy.IGNORED)
	private byte[] attContent;
}