package com.adtec.rdc.base.common.model.bo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author: dengchf     
 * @date:   2020年3月24日 
 * @Description:报表权限同步实体
 * @version V1.0 
 * @Copyright: adtec
 */
@Data
@Accessors(chain = true)
public class ReportAuth implements Serializable{
	
	public static final String TYPE_USER_ROLE = "0";
	public static final String TYPE_ROLE_USER = "1";
	
	private static final long serialVersionUID = 2651400834996322749L;
	/**	类型 */
	private String type;
	/**	主键id */
	private String userId;
	/**	角色id */
	private String roleId;
	/** 租户id */
	private String appId;
	
	private List<String> userIds;
	
	private List<String> roleIds;
}
