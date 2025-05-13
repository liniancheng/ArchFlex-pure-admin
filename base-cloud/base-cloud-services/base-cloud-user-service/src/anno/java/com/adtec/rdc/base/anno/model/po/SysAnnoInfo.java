package com.adtec.rdc.base.anno.model.po;

import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 公告信息表
 * </p>
 *
 * @author dengchf
 * @date 2019-12-01 20:25:26
 */
@Data
@Accessors(chain = true)
public class SysAnnoInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 公告id
	 */
	@TableId(value = "anno_id", type = IdType.UUID)
	private String annoId;
	/**
	 * 租户id
	 */
	@TableField(value = "app_id", strategy = FieldStrategy.IGNORED)
	private String appId;
	/**
	 * 公告等级
	 */
	@TableField(value = "anno_level", strategy = FieldStrategy.IGNORED)
	private String annoLevel;
	/**
	 * 是否有效
	 */
	@TableField(value = "is_valid", strategy = FieldStrategy.IGNORED)
	private Integer isValid;
	/**
	 * 公告标题
	 */
	@TableField(value = "anno_title", strategy = FieldStrategy.IGNORED)
	private String annoTitle;
	/**
	 * 公告内容
	 */
	@TableField(value = "anno_content", strategy = FieldStrategy.IGNORED)
	private String annoContent;
	/**
	 * 登录名
	 */
	@TableField(value = "login_name", strategy = FieldStrategy.IGNORED)
	private String loginName;
	/**
	 * 公告类型id
	 */
	@TableField(value = "type_id", strategy = FieldStrategy.IGNORED)
	private String typeId;
	/**
	 * 公告创建时间
	 */
	@TableField(value = "create_time", strategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;
	/**
	 * 公告修改时间
	 */
	@TableField(value = "modify_time", strategy = FieldStrategy.IGNORED)
	private LocalDateTime modifyTime;
	/**
	 * 公告有效期：生效时间
	 */
	@TableField(value = "start_time", strategy = FieldStrategy.IGNORED)
	private Date startTime;
	/**
	 * 公告有效期：结束时间
	 */
	@TableField(value = "end_time", strategy = FieldStrategy.IGNORED)
	private Date endTime;

	@TableField(exist = false)
	private List<SysAnnoAttach> fileList;
	
	@TableField(exist = false)
	private String userId;
	
	@TableField(exist = false)
	private String typeName;
	
	@TableField(exist = false)
	private String readFlag;
	
	@TableField(exist = false)
	private String deleFlag;

	@Override
	public String toString() {
		return "SysAnnoInfo [annoId=" + annoId + ", annoLevel=" + annoLevel + ", isValid=" + isValid + ", annoTitle="
				+ annoTitle + ", annoContent=" + annoContent + ", loginName=" + loginName + ", typeId=" + typeId
				+ ", createTime=" + createTime + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}