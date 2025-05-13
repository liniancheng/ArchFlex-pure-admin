package com.adtec.rdc.base.workflow.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.workflow.callback.CallbackService;
import com.adtec.rdc.base.workflow.engine.WorkflowEngine;
import com.adtec.rdc.base.workflow.engine.WorkflowNode;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.enums.WorkflowConstants;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowCreate;
import com.adtec.rdc.base.workflow.feign.bo.WorkflowQuery;
import com.adtec.rdc.base.workflow.mapper.SysUserTaskExtMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeAuthMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeOperMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowMacroInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeAuthMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeInfoMapper;
import com.adtec.rdc.base.workflow.mapper.WorkflowMapper;
import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.adtec.rdc.base.workflow.model.po.SysUserTaskExt;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.adtec.rdc.base.workflow.model.query.WorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.WorkflowService;
import com.adtec.rdc.base.workflow.util.SysWorkflowUtils;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class WorkflowServiceImpl extends BaseServiceImpl<WorkflowMapper, Workflow> implements WorkflowService {
	@Autowired
    private WorkflowMapper mapper;
	@Autowired
    private SysWorkflowInfoMapper sysWorkflowInfoMapper;
	@Autowired
    private SysWorkflowNodeInfoMapper sysWorkflowNodeMapper;
	@Autowired
    private SysWorkflowNodeAuthMapper sysWorkflowNodeAuthMapper;
	@Autowired
    private SysWorkflowMacroInfoMapper sysWorkflowMacroInfoMapper;
	@Autowired
    private SysWorkflowInstanceMapper sysWorkflowInstanceMapper;
	@Autowired
    private SysWorkflowInstanceNodeMapper sysWorkflowInstanceNodeMapper;
	@Autowired
    private SysWorkflowInstanceNodeAuthMapper sysWorkflowInstanceNodeAuthMapper;
	@Autowired
    private SysWorkflowInstanceNodeOperMapper sysWorkflowInstanceNodeOperMapper;
	@Autowired
    private CallbackService callbackService;
	@Autowired
    private SysUserTaskExtMapper sysUserTaskExtMapper;
	@Autowired
    private SysUserInfoMapper sysUserInfoMapper;

	@Override
	public WorkflowInfoQuery pageByQuery(WorkflowInfoQuery query) {
		//设置扩展任务sql
		QueryWrapper<SysUserTaskExt> taskExtQuery = new QueryWrapper<SysUserTaskExt>();
		taskExtQuery.lambda().in(SysUserTaskExt::getAppId, query.getAppId());
		List<SysUserTaskExt> taskExts = sysUserTaskExtMapper.selectList(taskExtQuery);
		if(taskExts.size()>0) {
			List<String> undoSqls = new ArrayList<String>(taskExts.size());
			List<String> doneSqls = new ArrayList<String>(taskExts.size());
			for(SysUserTaskExt taskExt : taskExts) {
				if(!StringUtils.isEmpty(taskExt.getUndoSql())) {
					undoSqls.add(taskExt.getUndoSql());
				}
				if(!StringUtils.isEmpty(taskExt.getDoneSql())) {
					doneSqls.add(taskExt.getDoneSql());
				}
			}
			query.setTaskExtDoneSqls(doneSqls);
			query.setTaskExtUndoSqls(undoSqls);
		}
		mapper.pageByQuery(query);
		List<Workflow> workflows = query.getRecords();
		if(workflows.size()>0) {
			List<String> inodeIds = new ArrayList<String>(workflows.size());
			for(Workflow workflow : workflows) {
				inodeIds.add(workflow.getInodeId());
				workflow.setOpers(new ArrayList<SysWorkflowInstanceNodeOper>());
			}
			List<SysWorkflowInstanceNodeOper> opers = sysWorkflowInstanceNodeOperMapper.queryNodeOpersByInodeIds(inodeIds);			
			for(Workflow workflow : workflows) {
				for(SysWorkflowInstanceNodeOper oper : opers) {
					if(oper.getInodeId().equals(workflow.getInodeId())) {
						workflow.getOpers().add(oper);
					}
				}	
			}	
		}
		return query;
	}

	@Override
	@Transactional
	public String create(WorkflowCreate workflowCreate) {
		if(StringUtils.isEmpty(workflowCreate.getWorkflowCode())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_INSTANCE_CODE_EMPTY.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_INSTANCE_CODE_EMPTY.getMessage());
		}
		List<SysWorkflowInfo> workflows = sysWorkflowInfoMapper.queryWorkflowListForCreate(workflowCreate.getWorkflowCode(), workflowCreate.getAppId());
		if(workflows.size()==0) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_INSTANCE_CODE_NOTEXIST.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_INSTANCE_CODE_NOTEXIST.getMessage());
		}
		SysWorkflowInfo workflow = null;
		if(StringUtils.isEmpty(workflowCreate.getVersionNum())) {
			for(SysWorkflowInfo info : workflows) {
				if(!StringUtils.isEmpty(info.getVersionMax()) && "1".equals(info.getVersionMax())) {
					workflow = info;
					break;
				}
			}
		}else {
			for(SysWorkflowInfo info : workflows) {
				if(!StringUtils.isEmpty(info.getVersionNum()) && workflowCreate.getVersionNum().equals(info.getVersionNum())) {
					workflow = info;
					break;
				}
			}
		}
		if(workflow==null) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_INSTANCE_VERSION_NOTEXIST.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_INSTANCE_VERSION_NOTEXIST.getMessage());
		}
		SysWorkflowInstance instance = createInstance(workflow, workflowCreate);
		List<SysWorkflowInstanceNode> instanceNodes = new ArrayList<SysWorkflowInstanceNode>();
		List<SysWorkflowNodeInfo> workflowNodes = createInstanceNodes(instance, instanceNodes);
		List<SysWorkflowInstanceNodeAuth> instanceNodeAuths = new ArrayList<SysWorkflowInstanceNodeAuth>();
		Map<String, String> macroCodeAndInodeIdMap = new HashMap<String, String>();
		List<SysWorkflowNodeAuth> workflowNodeAuths = createInstanceNodeAuths(instanceNodes, instanceNodeAuths, macroCodeAndInodeIdMap);
		//处理宏变量
		processMacros(workflow, workflowCreate.getMacroMap(), macroCodeAndInodeIdMap);
		
		return instance.getInstanceId();
	}
	
	private void processMacros(SysWorkflowInfo workflow, Map<String, Object> map, Map<String, String> macroCodeAndInodeIdMap) {
		if(map == null || macroCodeAndInodeIdMap.size() == 0) {
			return;
		}
		QueryWrapper<SysWorkflowMacroInfo> macroQuery = new QueryWrapper<SysWorkflowMacroInfo>();
		macroQuery.lambda().eq(SysWorkflowMacroInfo::getWorkflowId, workflow.getWorkflowId())
			.in(SysWorkflowMacroInfo::getMacroCode, macroCodeAndInodeIdMap.keySet());
		List<SysWorkflowMacroInfo> macros = sysWorkflowMacroInfoMapper.selectList(macroQuery);
		List<SysWorkflowInstanceNodeAuth> auths = new ArrayList<SysWorkflowInstanceNodeAuth>();
		for(SysWorkflowMacroInfo macro : macros) {
			processMacro(macro, macroCodeAndInodeIdMap.get(macro.getMacroCode()), map.get(macro.getMacroCode()), auths);
		}
		if(auths.size()>0) {
			sysWorkflowInstanceNodeAuthMapper.batchInsert(auths);
		}
	}
	
	private void processMacro(SysWorkflowMacroInfo macro, String inodeId, Object value, List<SysWorkflowInstanceNodeAuth> auths) {
		if(value == null) {
			return;
		}
		List<String> ids = (List<String>) value;
		for(String id : ids) {
			SysWorkflowInstanceNodeAuth auth = new SysWorkflowInstanceNodeAuth();
			auth.setAuthId(UUID.generate());
			if(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_ROLE.equals(macro.getMacroType())) {
				auth.setAuthType(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_ROLE);
			}else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_USER.equals(macro.getMacroType())) {
				auth.setAuthType(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_USER);
			}else {
				continue;
			}
			auth.setCreateTime(LocalDateTime.now());
			auth.setInodeId(inodeId);
			auth.setObjId(id);
			auths.add(auth);
		}
	}
	
	private List<SysWorkflowNodeAuth> createInstanceNodeAuths(List<SysWorkflowInstanceNode> instanceNodes, 
			List<SysWorkflowInstanceNodeAuth> instanceNodeAuths, Map<String, String> macroCodeAndInodeIdMap) {
		if(instanceNodes == null || instanceNodes.size()==0) {
			return null;
		}
		Map<String, String> nodeIdAndInodeIdMap = new HashMap<String, String>(instanceNodes.size());
		for(SysWorkflowInstanceNode instanceNode : instanceNodes) {
			nodeIdAndInodeIdMap.put(instanceNode.getNodeId(), instanceNode.getInodeId());
		}
		//节点权限
		QueryWrapper<SysWorkflowNodeAuth> authQuery = new QueryWrapper<SysWorkflowNodeAuth>();
		authQuery.lambda().in(SysWorkflowNodeAuth::getNodeId, nodeIdAndInodeIdMap.keySet());
		List<SysWorkflowNodeAuth> auths = sysWorkflowNodeAuthMapper.selectList(authQuery);
		if(auths.size()>0) {
			for(SysWorkflowNodeAuth auth : auths) {
				String inodeId = nodeIdAndInodeIdMap.get(auth.getNodeId());
				if(inodeId == null) {
					continue;
				}
				if(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_ROLE.equals(auth.getAuthType())
						|| WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_USER.equals(auth.getAuthType())) {
					SysWorkflowInstanceNodeAuth instanceNodeAuth = new SysWorkflowInstanceNodeAuth();
					BeanUtils.copyProperties(auth, instanceNodeAuth);
					instanceNodeAuth.setAuthId(UUID.generate());
					instanceNodeAuth.setInodeId(inodeId);
					instanceNodeAuths.add(instanceNodeAuth);
				}else if(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_ROLE_MACRO.equals(auth.getAuthType())
						|| WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_USER_MACRO.equals(auth.getAuthType())) {
					macroCodeAndInodeIdMap.put(auth.getObjId(), inodeId);
				}
		    }
			if(instanceNodeAuths.size()>0) {
				sysWorkflowInstanceNodeAuthMapper.batchInsert(instanceNodeAuths);				
			}
		}
		return auths;
	}
	
	private List<SysWorkflowNodeInfo> createInstanceNodes(SysWorkflowInstance instance, List<SysWorkflowInstanceNode> instanceNodes) {
		//创建节点
		QueryWrapper<SysWorkflowNodeInfo> nodeQuery = new QueryWrapper<SysWorkflowNodeInfo>();
		nodeQuery.lambda().eq(SysWorkflowNodeInfo::getWorkflowId, instance.getWorkflowId());
		List<SysWorkflowNodeInfo> nodes = sysWorkflowNodeMapper.selectList(nodeQuery);
		if(nodes.size()==0) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_INSTANCE_NOT_NODE.getErrorCode(),
				ErrorCodeEnums.WORKFLOW_INSTANCE_NOT_NODE.getMessage());
		}
		List<String> nodeIds = new ArrayList<String>();
		
		for(SysWorkflowNodeInfo node : nodes) {
			SysWorkflowInstanceNode instanceNode = new SysWorkflowInstanceNode();
			instanceNode.setInodeId(UUID.generate());
			instanceNode.setNodeId(node.getWfnodeId());
			instanceNode.setInstanceId(instance.getInstanceId());
			instanceNode.setNodeStatus(WorkflowConstants.INSTANCE_NODE_STATUS_UNDO);
			if("-1,".equals(node.getParentIds())) {
				instanceNode.setActiveFlag("1");
			}else {
				instanceNode.setActiveFlag("0");
			}
			instanceNodes.add(instanceNode);
			nodeIds.add(node.getWfnodeId());
		}
		sysWorkflowInstanceNodeMapper.batchInsert(instanceNodes);
		return nodes;
	}

	private SysWorkflowInstance createInstance(SysWorkflowInfo workflow, WorkflowCreate workflowCreate) {
		SysWorkflowInstance instance = new SysWorkflowInstance();
		instance.setInstanceId(UUID.generate());
		instance.setAppId(workflow.getAppId());
		instance.setCreateTime(LocalDateTime.now());
		instance.setCreateUser(workflowCreate.getUserId());
		if(StringUtils.isEmpty(workflowCreate.getInstanceName())) {
			instance.setInstanceName(workflow.getWorkflowName()+"_"+DateUtil.format(new Date(), "yyyyMMddHHmmsss"));
		}else {
			instance.setInstanceName(workflowCreate.getInstanceName());
		}
		instance.setInstanceStatus(WorkflowConstants.INSTANCE_STATUS_DOING);
		instance.setInstanceRmk("");
		instance.setWorkflowId(workflow.getWorkflowId());
		sysWorkflowInstanceMapper.insert(instance);
		return instance;
	}

	@Override
	public WorkflowQuery query(String instanceId) {
		SysWorkflowInstance instance = sysWorkflowInstanceMapper.selectById(instanceId);
		if(instance == null) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_INSTANCE_NOTEXIST.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_INSTANCE_NOTEXIST.getMessage());
		}
		// 所有实例节点
    	List<SysWorkflowInstanceNode> nodes = sysWorkflowInstanceNodeMapper.queryNodesByInstanceId(instanceId);
    	List<WorkflowNode> workflowNodes = null;
    	if(nodes.size()>0) {
        	List<SysWorkflowInstanceNodeOper> opers = sysWorkflowInstanceNodeOperMapper.queryNodeOpersByInstanceId(instanceId);
        	workflowNodes = WorkflowEngine.nodes(nodes, opers);
    	}
		return SysWorkflowUtils.getWorkflowQuery(instance, workflowNodes);
	}

	@Override
	public List<WorkflowQuery> status(List<String> instanceIds) {
		if(instanceIds == null || instanceIds.size() == 0) {
			return new ArrayList<WorkflowQuery>(0);
		}
		QueryWrapper<SysWorkflowInstance> query = new QueryWrapper<SysWorkflowInstance>();
		query.lambda().in(SysWorkflowInstance::getInstanceId, instanceIds);
		List<SysWorkflowInstance> instances = sysWorkflowInstanceMapper.selectList(query);
		return SysWorkflowUtils.getWorkflowStatus(instances, instanceIds);
	}

	@Override
	@Transactional
	public SysWorkflowInstanceNodeOper update(Workflow wf, String userId, String loginName) {
		//检核实例
		SysWorkflowInstance instance = sysWorkflowInstanceMapper.selectById(wf.getInstanceId());
		if(instance == null) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_RIGHT_USER.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_RIGHT_USER.getMessage());
		}else if(WorkflowConstants.INSTANCE_STATUS_DONE.equals(instance.getInstanceStatus())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_INSTANCE_FINISHED.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_OPER_INSTANCE_FINISHED.getMessage());
		}
		//检查工作流
		SysWorkflowInfo workflow = sysWorkflowInfoMapper.selectById(instance.getWorkflowId());
		if(workflow == null) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_RIGHT_USER.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_RIGHT_USER.getMessage());
		}
		//检核实例节点
		List<SysWorkflowInstanceNode> instanceNodes = sysWorkflowInstanceNodeMapper.queryNodesByInstanceId(instance.getInstanceId());
		SysWorkflowInstanceNode currentInstanceNode = null;
		for(SysWorkflowInstanceNode instanceNode : instanceNodes) {
			if(instanceNode.getInodeId().equals(wf.getInodeId())) {
				currentInstanceNode = instanceNode;
				break;
			}
		}
		if(currentInstanceNode == null) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_EXIST_INSTANCE_NODE.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_OPER_NOT_EXIST_INSTANCE_NODE.getMessage());
		}else if(!"1".equals(currentInstanceNode.getActiveFlag())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_INSTANCE_NODE_FINISHED.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_OPER_INSTANCE_NODE_FINISHED.getMessage());
		}
		//检核是否已审批
		List<SysWorkflowInstanceNodeOper> instanceNodeOpers = sysWorkflowInstanceNodeOperMapper.queryNodeOpersByInstanceId(instance.getInstanceId());
		for(SysWorkflowInstanceNodeOper instanceNodeOper : instanceNodeOpers) {
			if(instanceNodeOper.getLoginName().equals(loginName) && instanceNodeOper.getInodeId().equals(wf.getInodeId())) {
				throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_EXIST_USER_OPER.getErrorCode(),
						ErrorCodeEnums.WORKFLOW_NODE_OPER_EXIST_USER_OPER.getMessage());
			}
		}
		
		//写入操作
		SysWorkflowInstanceNodeOper newInstanceNodeOper = new SysWorkflowInstanceNodeOper();
		newInstanceNodeOper.setAgreeFlag(wf.getAgreeFlag());
		newInstanceNodeOper.setInstanceId(wf.getInstanceId());
		newInstanceNodeOper.setLoginName(loginName);
		newInstanceNodeOper.setInodeId(wf.getInodeId());
		newInstanceNodeOper.setOperId(UUID.generate());
		newInstanceNodeOper.setOperRmk(wf.getOperRmk());
		newInstanceNodeOper.setOperTime(new Date());
		sysWorkflowInstanceNodeOperMapper.insert(newInstanceNodeOper);
		instanceNodeOpers.add(newInstanceNodeOper);
		
		//更新节点
		Map<String, Integer> nodeOperMap = new HashMap<String, Integer>();
		for(SysWorkflowInstanceNodeOper instanceNodeOper : instanceNodeOpers) {
			if("1".equals(newInstanceNodeOper.getAgreeFlag())) {
				if("1".equals(instanceNodeOper.getAgreeFlag())) {
					Integer num = nodeOperMap.get(instanceNodeOper.getInodeId());
					if(num == null) {
						num = 1;
					}else {
						num++;
					}
					nodeOperMap.put(instanceNodeOper.getInodeId(), num);
				}
			}else if ("0".equals(newInstanceNodeOper.getAgreeFlag())){
				if("0".equals(instanceNodeOper.getAgreeFlag())) {
					Integer num = nodeOperMap.get(instanceNodeOper.getInodeId());
					if(num == null) {
						num = 1;
					}else {
						num++;
					}
					nodeOperMap.put(instanceNodeOper.getInodeId(), num);
				}
			}
		}
		if("1".equals(newInstanceNodeOper.getAgreeFlag())) {
			if(currentInstanceNode.getAgreeNum()>=nodeOperMap.get(currentInstanceNode.getInodeId())) {
				currentInstanceNode.setNodeStatus(WorkflowConstants.INSTANCE_NODE_STATUS_AGREE);
				currentInstanceNode.setActiveFlag("0");
			}else {
				currentInstanceNode.setNodeStatus(WorkflowConstants.INSTANCE_NODE_STATUS_DOING);
			}
		}else if ("0".equals(newInstanceNodeOper.getAgreeFlag())){
			if(currentInstanceNode.getDisagreeNum()>=nodeOperMap.get(currentInstanceNode.getInodeId())) {
				currentInstanceNode.setNodeStatus(WorkflowConstants.INSTANCE_NODE_STATUS_DISAGREE);
				currentInstanceNode.setActiveFlag("0");
			}else {
				currentInstanceNode.setNodeStatus(WorkflowConstants.INSTANCE_NODE_STATUS_DOING);
			}
		}
		sysWorkflowInstanceNodeMapper.updateById(currentInstanceNode);
		
		//更新实例或下级节点
		if(WorkflowConstants.INSTANCE_NODE_STATUS_DISAGREE.equals(currentInstanceNode.getNodeStatus())) {
			//退回时直接结束流程
			for(SysWorkflowInstanceNode instanceNode : instanceNodes) {
				if("1".equals(instanceNode.getActiveFlag())) {
					instanceNode.setActiveFlag("0");
					sysWorkflowInstanceNodeMapper.updateById(instanceNode);
				}
			}
			instance.setInstanceStatus(WorkflowConstants.INSTANCE_STATUS_DONE);
			instance.setModifyTime(LocalDateTime.now());
			sysWorkflowInstanceMapper.updateById(instance);
		}
		else if(WorkflowConstants.INSTANCE_NODE_STATUS_AGREE.equals(currentInstanceNode.getNodeStatus())) {
			//此节点结束时,判断同级节点是否全部结束
			boolean levelNodeFinished = true;
			List<SysWorkflowInstanceNode> nextLevelNodes = new ArrayList<SysWorkflowInstanceNode>();
			for(SysWorkflowInstanceNode instanceNode : instanceNodes) {
				if(instanceNode.getNodeLevel() == currentInstanceNode.getNodeLevel()) {
					if(WorkflowConstants.INSTANCE_NODE_STATUS_DOING.equals(instanceNode.getNodeStatus())
							|| WorkflowConstants.INSTANCE_NODE_STATUS_UNDO.equals(instanceNode.getNodeStatus())) {
						levelNodeFinished = false;
					}
				}else if(instanceNode.getNodeLevel() == (currentInstanceNode.getNodeLevel()+1)) {
					nextLevelNodes.add(instanceNode);
				}
			}
			if(levelNodeFinished) {
				//完成并且没有下级节点 - 更新实例为完成状态
				if(nextLevelNodes.size()==0) {
					instance.setInstanceStatus(WorkflowConstants.INSTANCE_STATUS_DONE);
					instance.setModifyTime(LocalDateTime.now());
					sysWorkflowInstanceMapper.updateById(instance);
				}
				//有下级节点 - 更新下级节点为活动节点
				else {
					for(SysWorkflowInstanceNode instanceNode : nextLevelNodes) {
						instanceNode.setActiveFlag("1");
						sysWorkflowInstanceNodeMapper.updateById(instanceNode);
					}
				}
			}
		}
		
		//回调
		if(!StringUtils.isEmpty(workflow.getFinishService()) && WorkflowConstants.INSTANCE_STATUS_DONE.equals(instance.getInstanceStatus())) {
			WorkflowQuery query = new WorkflowQuery();
			query.setInstanceId(instance.getInstanceId());
			query.setInstanceStatus(currentInstanceNode.getNodeStatus());//1通过 2未通过
			try {
				callbackService.callback(workflow.getFinishService(), query);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_OPER_CALLBACK_ERROR.getErrorCode(),
						ErrorCodeEnums.WORKFLOW_NODE_OPER_CALLBACK_ERROR.getMessage());
			}
		}
		
		newInstanceNodeOper.setUserName(sysUserInfoMapper.queryUserByLoginNameOrUserName(newInstanceNodeOper.getLoginName()).get(0).getUserName());
		
		return newInstanceNodeOper;
	}

}
