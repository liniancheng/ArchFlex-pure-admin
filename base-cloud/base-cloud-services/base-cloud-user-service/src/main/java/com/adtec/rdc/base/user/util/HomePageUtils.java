package com.adtec.rdc.base.user.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.anno.model.po.SysAnnoInfo;
import com.adtec.rdc.base.user.model.bo.MessageInfo;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.alibaba.druid.util.StringUtils;

/**
 * 基础首页工具类
 * @author JTao
 *
 */
public class HomePageUtils {
	/**
	 * 公告转成message
	 * @param anno
	 * @param annoTypeMap (key=typeId, value=typeName)
	 * @return
	 */
	public static MessageInfo annoChangeToMessage(SysAnnoInfo anno) {
		MessageInfo info = new MessageInfo();
		info.setId(anno.getAnnoId());
		info.setTypeId(anno.getTypeId());
		if(StringUtils.isEmpty(anno.getTypeName())) {
			info.setTypeName("系统公告");
		}
		info.setTitle(anno.getAnnoTitle());
		info.setLevel(anno.getAnnoLevel());
		info.setContent(anno.getAnnoContent());
		info.setFromTime(anno.getCreateTime());
//		info.setFromUserId(anno.getLoginName());
//		info.setStatus("1");
		info.setFromUserName(anno.getLoginName());
		info.setFromLoginName(anno.getLoginName());
		info.setStatus(anno.getReadFlag());
		return info;
	}

	/**
	 * 设置用户信息
	 * @param messages
	 * @param userMap
	 */
	public static void setMessageUserInfo(List<MessageInfo> messages, Map<String, SysUserInfo> userMap) {
		for(MessageInfo message : messages) {
			SysUserInfo user = userMap.get(message.getFromUserId());
			if(user != null) {
				message.setFromLoginName(user.getLoginName());
				message.setFromUserName(user.getUserName());
			}else {
				message.setFromLoginName("");
				message.setFromUserName("");
			}
		}
	}

	/**
	 * 是否存在未读消息
	 * @param messList
	 * @param annoList
	 * @return
	 */
	public static boolean isExistUnReadMessage(List<MessageInfo> messList, List<MessageInfo> annoList) {
		for(MessageInfo mess : annoList) {
			if(StringUtils.isEmpty(mess.getStatus()) || "0".equals(mess.getStatus())) {
				return true;
			}
		}
		for(MessageInfo mess : messList) {
			if(StringUtils.isEmpty(mess.getStatus()) || "0".equals(mess.getStatus())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否存在未处理任务
	 * @param taskList
	 * @return
	 */
	public static boolean isExistUnProcessTask(List<Workflow> workflows) {
		for(Workflow workflow : workflows) {
			if("0".equals(workflow.getOperStatus())) {
				return true;
			}
		}
		return false;
	}

	public static List<MessageInfo> changeToMessageInfoList(List<SysMessageInfo> messages) {
		List<MessageInfo> list = new ArrayList<MessageInfo>(messages.size());
		for(SysMessageInfo message : messages) {
			MessageInfo info = new MessageInfo();
			info.setContent(message.getMessageContent());
			info.setFromTime(message.getCreateTime());
			info.setId(message.getMessageId());
			info.setTitle(message.getMessageTitle());
			info.setStatus(message.getMessageStatus());
			list.add(info);
		}
		return list;
	}

}
