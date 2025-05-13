package com.adtec.rdc.base.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.rdc.base.anno.mapper.SysAnnoInfoMapper;
import com.adtec.rdc.base.anno.model.po.SysAnnoInfo;
import com.adtec.rdc.base.anno.model.query.SysAnnoInfoQuery;
import com.adtec.rdc.base.layout.service.SysLayoutInfoService;
import com.adtec.rdc.base.user.mapper.SysMessageInfoMapper;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.user.model.bo.BaseHomePage;
import com.adtec.rdc.base.user.model.bo.MessageInfo;
import com.adtec.rdc.base.user.model.po.SysMessageInfo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.user.model.query.SysMessageInfoQuery;
import com.adtec.rdc.base.user.service.BaseHomePageService;
import com.adtec.rdc.base.user.util.HomePageUtils;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.adtec.rdc.base.workflow.model.query.WorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.WorkflowService;

@Service
public class BaseHomePageServiceImpl implements BaseHomePageService {
	@Autowired
	private SysAnnoInfoMapper annoMapper;
	@Autowired
	private SysUserInfoMapper userMapper;
	@Autowired
	private SysMessageInfoMapper messageMapper;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private SysLayoutInfoService layoutService;

	@Override
	public BaseHomePage getBaseHomePage(String appId, String userId, String loginName, List<String> roleIds) {
		//消息&公告
		List<MessageInfo> annoList = getAnnoList(appId, userId);
		List<MessageInfo> messList = getMessList(appId, userId);
		if(annoList.size()>0 || messList.size()>0) {
			// 设置用户信息
			List<String> userIds = new ArrayList<String>();
			for(MessageInfo anno : annoList) {
				if(!userIds.contains(anno.getFromUserId())) {
					userIds.add(anno.getFromUserId());
				}
			}
			for(MessageInfo mess : messList) {
				if(!userIds.contains(mess.getFromUserId())) {
					userIds.add(mess.getFromUserId());
				}
			}
			List<SysUserInfo> userList = userMapper.selectBatchIds(userIds);
			Map<String, SysUserInfo> userMap = new HashMap<String, SysUserInfo>(userList.size());
			for(SysUserInfo user : userList) {
				userMap.put(user.getUserId(), user);
			}
			HomePageUtils.setMessageUserInfo(messList, userMap);
			HomePageUtils.setMessageUserInfo(annoList, userMap);
		}
		
		//任务
		List<Workflow> taskList = getTaskList(appId, userId, loginName, roleIds);
		
		BaseHomePage page = new BaseHomePage();
		page.setMessList(messList);
		page.setAnnoList(annoList);
		page.setTaskList(taskList);
		page.setExistUnReadMessage(HomePageUtils.isExistUnReadMessage(messList, annoList));
		page.setExistUnProcessTask(HomePageUtils.isExistUnProcessTask(taskList));
		page.setLayout(layoutService.fetchLayout(loginName, appId, roleIds));
		return page;
	}
	
	/**
	 * 任务列表
	 * @param appId
	 * @param userId
	 * @return
	 */
	private List<Workflow> getTaskList(String appId, String userId, String loginName, List<String> roleIds) {
		WorkflowInfoQuery query = new WorkflowInfoQuery();
		query.setLoginName(loginName);
    	query.setAppId(appId);
    	query.setUserId(userId);
    	query.setRoleIds(roleIds);
    	query.setCurrent(1);
    	query.setSize(10);
    	query.setStatus0("1");
    	query.setStatus1("1");
		return workflowService.pageByQuery(query).getRecords();
	}

	/**
	 * 消息列表
	 * @param appId
	 * @param userId
	 * @return
	 */
	private List<MessageInfo> getMessList(String appId, String userId) {
		SysMessageInfoQuery query = new SysMessageInfoQuery();
		query.setAppId(appId);
		query.setMessageRevUser(userId);
		query.setCurrent(1);
		query.setSize(10);
		List<SysMessageInfo> messages = messageMapper.pageByQuery(query).getRecords();
		return HomePageUtils.changeToMessageInfoList(messages);
	}

	/**
	 * 公告列表
	 * @param appId
	 * @return
	 */
	private List<MessageInfo> getAnnoList(String appId, String userId) {
		SysAnnoInfoQuery query = new SysAnnoInfoQuery();
		query.setAppId(appId);
		query.setUserId(userId);
		query.setIsValid(1);
		query.setEndTime(new Date());
		annoMapper.appPageByQuery(query);
		List<SysAnnoInfo> annos = query.getRecords();
		List<MessageInfo> annoList = new ArrayList<MessageInfo>(annos.size());
		Optional.ofNullable(annos).ifPresent(list -> {
			list.forEach(anno -> {
				annoList.add(HomePageUtils.annoChangeToMessage(anno));
			});
		});
		return annoList;
	}
}
