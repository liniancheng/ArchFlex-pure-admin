package com.adtec.rdc.base.workflow.service.impl;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.exception.ServiceException;
import com.adtec.rdc.base.workflow.enums.ErrorCodeEnums;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeAuthMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowNodeInfoMapper;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowNodeTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInfoQuery;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowNodeInfoQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowNodeInfoService;
import com.adtec.rdc.base.workflow.util.SysWorkflowNodeTreeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author adtec
 * @date 2020-06-30 09:14:21
 */
@Service
public class SysWorkflowNodeInfoServiceImpl extends BaseServiceImpl<SysWorkflowNodeInfoMapper, SysWorkflowNodeInfo> implements SysWorkflowNodeInfoService {
	@Autowired
    private SysWorkflowNodeInfoMapper mapper;
	@Autowired
    private SysWorkflowNodeAuthMapper authMapper;
	
	@Override
	public SysWorkflowNodeInfoQuery pageByQuery(SysWorkflowNodeInfoQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public List<SysWorkflowNodeTree> tree(SysWorkflowInfoQuery query) {
		QueryWrapper<SysWorkflowNodeInfo> nodeQuery = new QueryWrapper<SysWorkflowNodeInfo>();
		nodeQuery.lambda().eq(SysWorkflowNodeInfo::getWorkflowId, query.getWorkflowId()).orderByAsc(SysWorkflowNodeInfo::getCreateTime);
    	List<SysWorkflowNodeInfo> nodes = mapper.selectList(nodeQuery);
    	return SysWorkflowNodeTreeUtils.tree(nodes);
	}
	
	@Override
	@Transactional
	public boolean save(SysWorkflowNodeInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_RE_NAME.getMessage());
		}
		if(!entity.getParentIds().endsWith(",")) {
			entity.setParentIds(entity.getParentIds()+",");
		}
		entity.setCreateTime(LocalDateTime.now());
		return super.save(entity);
	}

	@Override
	@Transactional
	public boolean updateById(SysWorkflowNodeInfo entity) {
		if (isExistName(entity)) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_RE_NAME.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_RE_NAME.getMessage());
		}
		if(!entity.getParentIds().endsWith(",")) {
			entity.setParentIds(entity.getParentIds()+",");
		}
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		//检查是否存在元数据
		if(mapper.isExistChild(id.toString())) {
			throw new ServiceException(ErrorCodeEnums.WORKFLOW_NODE_HAS_CHILD.getErrorCode(),
					ErrorCodeEnums.WORKFLOW_NODE_HAS_CHILD.getMessage());
		}
		// 删除节点&节点权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("node_id", id);
		authMapper.deleteByMap(map);
		return super.removeById(id);
	}
	
	private boolean isExistName(SysWorkflowNodeInfo entity) {
		return mapper.isExistName(entity);
	}

	@Override
	public List<SysWorkflowNodeTree> parentNodes(String workflowId, int nodeLevel) {
		QueryWrapper<SysWorkflowNodeInfo> nodeQuery = new QueryWrapper<SysWorkflowNodeInfo>();
		nodeQuery.lambda().eq(SysWorkflowNodeInfo::getWorkflowId, workflowId).eq(SysWorkflowNodeInfo::getWfnodeLevel, nodeLevel).orderByAsc(SysWorkflowNodeInfo::getCreateTime);
    	List<SysWorkflowNodeInfo> nodes = mapper.selectList(nodeQuery);
    	return SysWorkflowNodeTreeUtils.parentNodeTree(nodes);
	}
}
