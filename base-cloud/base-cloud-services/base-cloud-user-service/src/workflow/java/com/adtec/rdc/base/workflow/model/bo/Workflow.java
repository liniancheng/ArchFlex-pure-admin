package com.adtec.rdc.base.workflow.model.bo;

import java.util.Date;
import java.util.List;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;

import lombok.Data;

@Data
public class Workflow {
	private String instanceId;
	private String instanceName;
	private Date createTime;
	private String createUser;
	private String inodeId;
	private String nodeName;
	private String instanceStatus; //实例状态 0-未处理 1-处理中 2-已完成
	private String createUserName; //创建用户姓名
	private String detailUrl;//任务详情地址
	private List<SysWorkflowInstanceNodeOper> opers;
	
	private String operStatus; //用户处理状态 0-待处理 1-已处理
	private String operUser;
	private Date operTime;
	private String agreeFlag;
	private String operRmk;
}
