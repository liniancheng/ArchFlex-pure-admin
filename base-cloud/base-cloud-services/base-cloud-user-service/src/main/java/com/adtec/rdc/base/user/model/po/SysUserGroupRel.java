package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import lombok.Data;

/**
 *  <p>
 * 用户组-用户关系表
 * </p>
 * @author Liushp
 * 
 */
@Data
public class SysUserGroupRel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  用户id
	 */
	private String userId;
	
	/**
	 *  用户组id
	 */
	private String groupId;
}
