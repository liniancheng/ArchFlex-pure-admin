package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.common.util.UUID;
import com.adtec.rdc.base.user.mapper.SysRoleInfoMapper;
import com.adtec.rdc.base.user.mapper.SysUserInfoMapper;
import com.adtec.rdc.base.user.model.po.SysRoleInfo;
import com.adtec.rdc.base.user.model.po.SysUserInfo;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.enums.WorkflowConstants;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowMacroInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeAuthMapper;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeAuth;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowNodeAuthQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowNodeAuthService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-06-30 09:14:34
 */
@Service
public class SysWorkflowNodeAuthServiceImpl extends BaseServiceImpl<SysWorkflowNodeAuthMapper, SysWorkflowNodeAuth> implements SysWorkflowNodeAuthService {
	@Autowired
    private SysWorkflowNodeAuthMapper mapper;
	@Autowired
    private SysRoleInfoMapper roleMapper;
	@Autowired
    private SysUserInfoMapper userMapper;
	@Autowired
    private SysWorkflowMacroInfoMapper macroMapper;
	
	@Override
	public SysWorkflowNodeAuthQuery pageByQuery(SysWorkflowNodeAuthQuery query) {
		query.setAsc("auth_type", "obj_name");
		mapper.pageByQuery(query);
        return query;
	}
	
	@Override
	@Transactional
	public boolean save(SysWorkflowNodeAuth entity) {
		changeLoginNameToUserId(entity);
		List<String> existObjIds = mapper.queryObjIdsByNodeIdAndAuthType(entity.getNodeId(), entity.getAuthType());
		entity.setCreateTime(LocalDateTime.now());
		if(entity.getObjId().indexOf(",")>-1) {
			List<SysWorkflowNodeAuth> inserts = new ArrayList<SysWorkflowNodeAuth>();
			String[] arrayIds = entity.getObjId().split(",");
			for(String arrayId : arrayIds) {
				if(existObjIds.contains(arrayId)) {
					continue;
				}
				SysWorkflowNodeAuth auth = new SysWorkflowNodeAuth();
				BeanUtils.copyProperties(entity,  auth);
				auth.setAuthId(UUID.generate());
				auth.setObjId(arrayId);
				inserts.add(auth);
			}
			if(inserts.size()>0) {
				super.saveBatch(inserts);
			}
		}else {
			if(!existObjIds.contains(entity.getObjId())) {
				entity.setAuthId(UUID.generate());
				return super.save(entity);
			}
		}
		return true;
	}
	
	/**
	 * 如果是用户授权则检查objId并转成userId
	 * @param entity
	 */
	private void changeLoginNameToUserId(SysWorkflowNodeAuth entity) {
		if("user".equals(entity.getAuthType())) {
			String userId = null;
			List<SysUserInfo> users = userMapper.queryUserByLoginNameOrUserName(entity.getObjId());
			if(users.size()==1) {
				userId = users.get(0).getUserId();
			}
			if(userId == null) {
				throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_AUTH_NOT_EXIST_USER.getErrorCode(),
						ErrorCodeEnums.WORKFLOW_NODE_AUTH_NOT_EXIST_USER.getMessage()); 
			}else {
				entity.setObjId(userId);
			}
		}
	}

	@Override
	public List<SysRoleInfo> roles(String nodeId, String appId) {
		if(nodeId.indexOf(".")>-1){
			nodeId = nodeId.substring(nodeId.lastIndexOf(".")+1);
		}
		QueryWrapper<SysRoleInfo> roleQuery = new QueryWrapper<SysRoleInfo>();
		roleQuery.lambda().eq(SysRoleInfo::getAppId, appId).orderByAsc(SysRoleInfo::getRoleName);
    	List<SysRoleInfo> roles = roleMapper.selectList(roleQuery);
    	List<String> existObjIds = mapper.queryObjIdsByNodeIdAndAuthType(nodeId, "role");
    	if(existObjIds.size()>0) {
    		List<SysRoleInfo> list = new ArrayList<SysRoleInfo>(roles.size()-existObjIds.size());
    		for(SysRoleInfo role : roles) {
    			if(!existObjIds.contains(role.getRoleId())) {
    				list.add(role);
    			}
    		}
    		return list;
    	}else {
    		return roles;
    	}
	}

	@Override
	public List<SysWorkflowMacroInfo> macros(String workflowId, String nodeId, String authType) {
		if(nodeId.indexOf(".")>-1){
			nodeId = nodeId.substring(nodeId.lastIndexOf(".")+1);
		}
		String macroType = null;
		if(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_ROLE_MACRO.equals(authType)) {
			macroType = WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_ROLE;
		} else if(WorkflowConstants.WORKFLOW_NODE_AUTH_TYPE_USER_MACRO.equals(authType)) {
			macroType = WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_USER;
		}
		QueryWrapper<SysWorkflowMacroInfo> query = new QueryWrapper<SysWorkflowMacroInfo>();
		query.lambda().eq(SysWorkflowMacroInfo::getWorkflowId, workflowId)
			.eq(SysWorkflowMacroInfo::getMacroType, macroType).orderByAsc(SysWorkflowMacroInfo::getMacroCode);
    	List<SysWorkflowMacroInfo> macros = macroMapper.selectList(query);
    	List<String> existMacroCodes = mapper.queryObjIdsByNodeIdAndAuthType(nodeId, authType);
    	if(existMacroCodes.size()>0) {
    		List<SysWorkflowMacroInfo> list = new ArrayList<SysWorkflowMacroInfo>(macros.size()-existMacroCodes.size());
    		for(SysWorkflowMacroInfo macro : macros) {
    			if(!existMacroCodes.contains(macro.getMacroCode())) {
    				list.add(macro);
    			}
    		}
    		return list;
    	}else {
    		return macros;
    	}
	}
}
