package com.adtec.rdc.base.anno.model.query;

import java.util.Date;

import com.adtec.rdc.base.anno.model.po.SysAnnoInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author dengchf
 * @date 2019-12-01 20:25:26
 */
@Data
public class SysAnnoInfoQuery extends Page<SysAnnoInfo> {

	private static final long serialVersionUID = 6865661216108608016L;
	/**
	 * 公告id
	 */
	private String annoId;
	/**
	 * 租户id
	 */
	private String appId;
	/**
	 * 公告等级
	 */
	private String annoLevel;
	/**
	 * 是否有效
	 */
	private Integer isValid;
	/**
	 * 公告标题
	 */
	private String annoTitle;
	/**
	 * 公告内容
	 */
	private String annoContent;
	/**
	 * 登录名
	 */
	private String loginName;
	/**
	 * 公告类型id
	 */
	private String typeId;
	/**
	 * 公告创建时间
	 */
	private Date createTime;
	/**
	 * 公告修改时间
	 */
	private Date modifyTime;
	/**
	 * 公告有效期：生效时间
	 */
	private Date startTime;
	/**
	 * 公告有效期：结束时间
	 */
	private Date endTime;
	
	/**
	 * 与用户公告关系表关联的结果，用于判定是否已读
	 */
	private String userId;
	
	/**
	 * 公告类型名称
	 */
	private String typeName;
	
	/**
	 * 已读标志
	 */
	private String readFlag;
	
	/**
	 * 删除标志
	 */
	private String deleFlag;
	
	/**
	 * 查询专用
	 */
	private String keyword;

}
