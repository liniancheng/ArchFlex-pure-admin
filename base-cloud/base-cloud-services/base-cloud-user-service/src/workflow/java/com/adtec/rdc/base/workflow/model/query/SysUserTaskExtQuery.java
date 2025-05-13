package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-07-20 19:19:57
 */
@Data
public class SysUserTaskExtQuery extends Page<SysUserTaskExt> {

	/**
	 * 任务扩展ID
	 */
	private String extId;
	/**
	 * 任务扩展名称
	 */
	private String extName;
	/**
	 * 待处理查询SQL
	 */
	private String undoSql;
	/**
	 * 已处理查询SQL
	 */
	private String doneSql;
	/**
	 * 任务URL
	 */
	private String extUrl;
	/**
	 * 任务扩展描述
	 */
	private String extRmk;
	/**
	 * 租户ID
	 */
	private String appId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date modifyTime;

}
