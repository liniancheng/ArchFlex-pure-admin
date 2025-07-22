package com.littlelee.base.param.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SysParamInfo implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -3870921387546742986L;
	
	@TableId(value = "param_id", type = IdType.ASSIGN_UUID)
	private String paramId;
	
	@TableField(value = "param_name", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String paramName;
	
	@TableField(value = "param_value", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String paramValue;
	
	@TableField(value = "param_rmk", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String paramRmk;
	
	@TableField(value = "app_id", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String appId;
	
	@TableField(value = "create_time", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;
	
	@TableField(value = "modify_time", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private LocalDateTime modifyTime;
}
