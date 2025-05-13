package com.adtec.rdc.base.user.model.bo;

import java.time.LocalDateTime;
import java.util.List;

import com.adtec.rdc.base.anno.model.query.SysAnnoAttach;

import lombok.Data;

/**
 * 公告&消息
 * @author JTao
 *
 */
@Data
public class MessageInfo implements Comparable<MessageInfo> {
	private String id;//id
	private String typeId;//类型id
	private String typeName;//类型名称
	private String title;//标题
	private String content;//内容
	private String level;//级别
	private String fromUserId;//发送人
	private String fromLoginName;
	private String fromUserName;
	private LocalDateTime fromTime;//发送时间
	private String status;//是否已读
	private  List<SysAnnoAttach> attachList;//公告附件
	
	@Override
	public int compareTo(MessageInfo o) {
		return fromTime.compareTo(o.fromTime);
	}
}
