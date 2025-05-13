package com.adtec.rdc.base.anno.model.query;

import java.util.Date;

import com.adtec.rdc.base.anno.model.po.SysAnnoTypeInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2019-11-26 09:51:11
 */
@Data
public class SysAnnoTypeInfoQuery extends Page<SysAnnoTypeInfo> {
	
	private static final long serialVersionUID = -582928476821368345L;
	/**
	 * 主键id
	 */
	private String typeId;
	/**
	 * 类型名称
	 */
	private String typeName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 类型描述
	 */
	private String typeRmk;
	/**
	 * 租户id
	 */
	private String appId;

}
