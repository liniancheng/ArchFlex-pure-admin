package com.adtec.rdc.base.user.model.po;

import java.io.Serializable;

import lombok.Data;

/**
 *  <p>
 * 用户组-机构关系表
 * </p>
 * @author Liushp
 * 
 */
@Data
public class SysGroupBranchRel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *  机构号
	 */
	private String branchNo;
	
	/**
	 *  用户组id
	 */
	private String groupId;
}
