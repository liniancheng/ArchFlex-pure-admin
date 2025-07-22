package com.littlelee.base.anno.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * @author: littlelee
 * @date:   2020年9月3日 
 * @Description:
 * @version V1.0 
 * @Copyright: adtec
 */
@Data
@Accessors(chain = true)
public class SysAnnoUserOper implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6710069902087882700L;

	/**
	 * 公告id
	 */
	@TableId(value = "ANNO_ID", type = IdType.INPUT)
	private String annoId;
	/**
	 * 用户id
	 */
	@TableField(value = "USER_ID")
	private String userId;
	/**
	 * 已读标识
	 */
	@TableField(value = "READ_FLAG", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String readFlag;
	/**
	 * 删除标识
	 */
	@TableField(value = "DELE_FLAG", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String deleFlag;
}
