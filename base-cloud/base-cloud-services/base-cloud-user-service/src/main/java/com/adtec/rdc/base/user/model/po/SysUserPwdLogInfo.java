package com.adtec.rdc.base.user.model.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * 用户密码历史表
 * </p>
 *
 * @author dengchf
 * @date 2021-02-07 14:34:42
 */
@Data
@Accessors(chain = true)
public class SysUserPwdLogInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId(value = "LOG_ID", type = IdType.UUID)
	private String logId;

	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 登录名
	 */
	@TableField(value = "LOGIN_NAME", strategy = FieldStrategy.IGNORED, fill = FieldFill.INSERT)
	private String loginName;
	/**
	 * 密码
	 */
	@TableField(value = "LOGIN_PWD", strategy = FieldStrategy.IGNORED)
	private String loginPwd;
}