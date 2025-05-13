package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.enums.WorkflowConstants;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowMacroInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeAuthMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowTypeInfoMapper;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowCopy;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInfoService;
import com.adtec.rdc.base.workflow.util.SysWorkflowTreeUtils;
import com.adtec.rdc.base.workflow.util.SysWorkflowUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-06-30 09:15:26
 */
@Service
public class SysWorkflowInfoServiceImpl extends BaseServiceImpl<SysWorkflowInfoMapper, SysWorkflowInfo> implements SysWorkflowInfoService {
	@Autowired
    private SysWorkflowInfoMapper mapper;
	@Autowired
    private SysWorkflowNodeInfoMapper nodeMapper;
	@Autowired
    private SysWorkflowNodeAuthMapper nodeAuthMapper;
	@Autowired
    private SysWorkflowMacroInfoMapper macroMapper;
	@Autowired
    private SysWorkflowTypeInfoMapper typeMapper;
	
	private static final String INIT_VERSION_NUM = "1.0";
	
	@Override
	public SysWorkflowInfoQuery pageByQuery(SysWorkflowInfoQuery query) {
		query.setDesc("workflow_code", "version_num");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public List<SysWorkflowTree> tree(SysWorkflowInfoQuery query) {
		List<SysWorkflowInfo> workflows = mapper.queryWorkflowListForSearch(query.getWorkflowName(), query.getWorkflowRmk(), query.getAppId());
    	if(!StringUtils.isEmpty(query.getWorkflowName()) || !StringUtils.isEmpty(query.getWorkflowRmk())) {
    		return SysWorkflowTreeUtils.tree(workflows);
    	}else {
    		QueryWrapper<SysWorkflowTypeInfo> typeQuery = new QueryWrapper<SysWorkflowTypeInfo>();
    		typeQuery.lambda().eq(SysWorkflowTypeInfo::getAppId, query.getAppId()).orderByAsc(SysWorkflowTypeInfo::getTypeOrder);
        	List<SysWorkflowTypeInfo> types = typeMapper.selectList(typeQuery);
        	return SysWorkflowTreeUtils.tree(types, workflows);
    	}
	}
	
	@Override
	@Transactional
	public boolean save(SysWorkflowInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_RE_NAME.getMessage());
		}
		if (isExistCode(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_RE_CODE.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_RE_CODE.getMessage());
		}
		entity.setVersionNum(INIT_VERSION_NUM);
		entity.setVersionMax("1");
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysWorkflowInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_RE_NAME.getMessage());
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		//检查是否存在元数据
		if(mapper.isExistInstance(id.toString())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_HAS_INSTANCE.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_HAS_INSTANCE.getMessage());
		}
		// 删除节点权限
		nodeAuthMapper.deleteByWorkflowId(id);
		// 删除节点
		nodeMapper.deleteByWorkflowId(id);
		// 删除宏
		macroMapper.deleteByWorkflowId(id);
		// 更新versionMax
		SysWorkflowInfo info = mapper.selectById(id);
			// 不是第一个版本 并且 是当前最大版本时
		if(!INIT_VERSION_NUM.equals(info.getVersionNum()) && "1".equals(info.getVersionMax())) {
			// 找前一个版本
			SysWorkflowInfo newVersionMaxInfo = mapper.selectPreviousVersionInfoByWorkflowCodeAndVersionNum(info.getWorkflowCode(), info.getVersionNum());
			if(newVersionMaxInfo != null) {
				newVersionMaxInfo.setVersionMax("1");
				mapper.updateById(newVersionMaxInfo);
			}
		}
		return super.removeById(id);
	}
	
	private boolean isExistName(SysWorkflowInfo entity) {
		return mapper.isExistName(entity);
	}
	
	private boolean isExistCode(SysWorkflowInfo entity) {
		return mapper.isExistCode(entity);
	}

	@Override
	@Transactional
	public Boolean copy(SysWorkflowCopy sysWorkflowCopy) {
		//老版本
		SysWorkflowInfo oldInfo = mapper.selectById(sysWorkflowCopy.getWorkflowId());
		SysWorkflowInfo newInfo = oldInfo.clone();
		oldInfo.setVersionMax("0");
		mapper.updateById(oldInfo);
		//新版本
		newInfo.setCreateTime(LocalDateTime.now());
		newInfo.setModifyTime(null);
		newInfo.setWorkflowId(UUID.generate());
		newInfo.setVersionNum(SysWorkflowUtils.getNewVersionNum(oldInfo.getVersionNum(), sysWorkflowCopy.getVersionType()));
		mapper.insert(newInfo);
		if(!StringUtils.isEmpty(sysWorkflowCopy.getCopyType()) 
				&& !WorkflowConstants.WORKFLOW_COPY_TYPE_ONLY_WORKFLOW.equals(sysWorkflowCopy.getCopyType())) {
			List<SysWorkflowNodeInfo> nodes = null;
			List<SysWorkflowNodeAuth> auths = null;
			Map<String, String> oldAndNewNodeIdMap = new HashMap<String, String>();
			if(WorkflowConstants.WORKFLOW_COPY_TYPE_WORKFLOW_AND_NODE.equals(sysWorkflowCopy.getCopyType())) {
				//复制节点
				nodes = getNewNodes(sysWorkflowCopy.getWorkflowId(), newInfo.getWorkflowId(), oldAndNewNodeIdMap); 
			}else if(WorkflowConstants.WORKFLOW_COPY_TYPE_WORKFLOW_AND_NODE_AND_AUTH.equals(sysWorkflowCopy.getCopyType())) {
				//复制节点和权限
				nodes = getNewNodes(sysWorkflowCopy.getWorkflowId(), newInfo.getWorkflowId(), oldAndNewNodeIdMap); 
				auths = getNewAuths(sysWorkflowCopy.getWorkflowId(), oldAndNewNodeIdMap); 
			}
			if(nodes != null && nodes.size()>0) {
				nodeMapper.batchInsert(nodes);
			}
			if(auths != null && auths.size()>0) {
				nodeAuthMapper.batchInsert(auths);
			}
		}
		//复制宏
		List<SysWorkflowMacroInfo> macros = getMacros(sysWorkflowCopy.getWorkflowId(), newInfo.getWorkflowId(), sysWorkflowCopy.getCopyType());
		if(macros.size()>0) {
			macroMapper.batchInsert(macros);
		}
		
		return true;
	}
	
	private List<SysWorkflowMacroInfo> getMacros(String oldWorkflowId, String newWorkflowId, String copyType) {
		QueryWrapper<SysWorkflowMacroInfo> query = new QueryWrapper<SysWorkflowMacroInfo>();
		query.lambda().in(SysWorkflowMacroInfo::getWorkflowId, oldWorkflowId);
    	List<SysWorkflowMacroInfo> oldMacros = macroMapper.selectList(query);
    	if(oldMacros.size()==0) {
    		return null;
    	}
    	List<SysWorkflowMacroInfo> macros = new ArrayList<SysWorkflowMacroInfo>(oldMacros.size());
    	for(SysWorkflowMacroInfo oldMacro : oldMacros) {
    		//如果是流程宏或实例宏,直接复制
    		if(WorkflowConstants.WORKFLOW_MACRO_TYPE_WORKFLOW.equals(oldMacro.getMacroType())
    				|| WorkflowConstants.WORKFLOW_MACRO_TYPE_INSTANCE.equals(oldMacro.getMacroType())) {
    			macros.add(cloneMacro(oldMacro, newWorkflowId));
    		}
    		//如果是节点宏,如果复制节点,则复制
    		else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_NODE.equals(oldMacro.getMacroType())
    				&& !WorkflowConstants.WORKFLOW_COPY_TYPE_ONLY_WORKFLOW.equals(copyType)) {
    			macros.add(cloneMacro(oldMacro, newWorkflowId));
    		}
    		//如果是权限宏,如果复制权限,则复制
    		else if((WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_ROLE.equals(oldMacro.getMacroType())
    				|| WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_USER.equals(oldMacro.getMacroType()))
    				&& WorkflowConstants.WORKFLOW_COPY_TYPE_WORKFLOW_AND_NODE_AND_AUTH.equals(copyType)) {
    			macros.add(cloneMacro(oldMacro, newWorkflowId));
    		}
    	}
    	return macros;
	}
	
	private SysWorkflowMacroInfo cloneMacro(SysWorkflowMacroInfo oldMacro, String newWorkflowId) {
		SysWorkflowMacroInfo macro = oldMacro.clone();
		macro.setMacroId(UUID.generate());
		macro.setCreateTime(LocalDateTime.now());
		macro.setWorkflowId(newWorkflowId);
		return macro;
	}

	private List<SysWorkflowNodeAuth> getNewAuths(String workflowId, Map<String, String> oldAndNewNodeIdMap) {
		if(oldAndNewNodeIdMap.size()==0) {
			return null;
		}
		QueryWrapper<SysWorkflowNodeAuth> query = new QueryWrapper<SysWorkflowNodeAuth>();
		query.lambda().in(SysWorkflowNodeAuth::getNodeId, oldAndNewNodeIdMap.keySet());
    	List<SysWorkflowNodeAuth> oldAuths = nodeAuthMapper.selectList(query);
    	if(oldAuths.size()==0) {
    		return null;
    	}
    	List<SysWorkflowNodeAuth> auths = new ArrayList<SysWorkflowNodeAuth>(oldAuths.size());
    	for(SysWorkflowNodeAuth oldAuth : oldAuths) {
    		SysWorkflowNodeAuth auth = oldAuth.clone();
    		auth.setAuthId(UUID.generate());
    		auth.setCreateTime(LocalDateTime.now());
    		auth.setNodeId(oldAndNewNodeIdMap.get(oldAuth.getNodeId()));
    		auths.add(auth);
    	}
    	return auths;
	}

	private List<SysWorkflowNodeInfo> getNewNodes(String oldWorkflowId, String newWorkflowId, Map<String, String> oldAndNewNodeIdMap) {
		QueryWrapper<SysWorkflowNodeInfo> query = new QueryWrapper<SysWorkflowNodeInfo>();
		query.lambda().eq(SysWorkflowNodeInfo::getWorkflowId, oldWorkflowId);
    	List<SysWorkflowNodeInfo> oldNodes = nodeMapper.selectList(query);
    	if(oldNodes.size()==0) {
    		return null;
    	}
    	List<SysWorkflowNodeInfo> nodes = new ArrayList<SysWorkflowNodeInfo>(oldNodes.size());
    	for(SysWorkflowNodeInfo oldNode : oldNodes) {
    		SysWorkflowNodeInfo node = oldNode.clone();
    		node.setWorkflowId(newWorkflowId);
    		node.setWfnodeId(UUID.generate());
    		node.setCreateTime(LocalDateTime.now());
    		node.setModifyTime(null);
    		nodes.add(node);
    		oldAndNewNodeIdMap.put(oldNode.getWfnodeId(), node.getWfnodeId());
    	}
    	//替换上级id
    	for(SysWorkflowNodeInfo node : nodes) {
    		for(String oldNodeId : oldAndNewNodeIdMap.keySet()) {
    			String newNodeId = oldAndNewNodeIdMap.get(oldNodeId);
    			node.setParentIds(node.getParentIds().replace(oldNodeId+",", newNodeId+","));
    		}
    	}
    	return nodes;
	}
}
