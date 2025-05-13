package com.adtec.rdc.base.user.model.bo;

import java.util.List;

import com.adtec.rdc.base.layout.model.po.SysLayoutInfo;
import com.adtec.rdc.base.workflow.model.bo.Workflow;

import lombok.Data;

/**
 * 首页基础框架相关内容封装
 * @author JTao
 *
 */
@Data
public class BaseHomePage {
	private List<MessageInfo> annoList; //公告
	private List<MessageInfo> messList; //消息
	private List<Workflow> taskList; //任务
	private boolean existUnReadMessage; //是否有未读的消息
	private boolean existUnProcessTask; //是否有未处理的任务
	
	private SysLayoutInfo layout;// 首页布局
}
