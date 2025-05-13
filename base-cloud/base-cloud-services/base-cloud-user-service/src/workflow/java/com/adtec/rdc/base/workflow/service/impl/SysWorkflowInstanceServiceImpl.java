package com.adtec.rdc.base.workflow.service.impl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adtec.rdc.base.common.base.service.impl.BaseServiceImpl;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphBean;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphEdgeBean;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphVertexBean;
import com.adtec.rdc.base.workflow.engine.WorkflowEngine;
import com.adtec.rdc.base.workflow.engine.WorkflowGraphEngine;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInfoMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeAuthMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeMapper;
import com.adtec.rdc.base.workflow.mapper.SysWorkflowInstanceNodeOperMapper;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;
import com.adtec.rdc.base.workflow.model.query.SysWorkflowInstanceQuery;
import com.adtec.rdc.base.workflow.service.SysWorkflowInstanceService;
import com.adtec.rdc.base.workflow.util.SysWorkflowTreeUtils;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author adtec
 * @date 2020-06-30 09:15:15
 */
@Service
public class SysWorkflowInstanceServiceImpl extends BaseServiceImpl<SysWorkflowInstanceMapper, SysWorkflowInstance> implements SysWorkflowInstanceService {
	@Autowired
    private SysWorkflowInstanceMapper mapper;
	@Autowired
    private SysWorkflowInfoMapper workflowMapper;
	@Autowired
    private SysWorkflowInstanceNodeMapper instanceNodeMapper;
	@Autowired
    private SysWorkflowInstanceNodeOperMapper instanceNodeOperMapper;
	@Autowired
    private SysWorkflowInstanceNodeAuthMapper instanceNodeAuthMapper;
	
	@Override
	public SysWorkflowInstanceQuery pageByQuery(SysWorkflowInstanceQuery query) {
		query.setDesc("create_time", "modify_time");
		mapper.pageByQuery(query);
        return query;
	}

	@Override
	public List<SysWorkflowTree> tree(SysWorkflowInstanceQuery query) {
		List<SysWorkflowInstance> instances = mapper.queryInstanceListForSearch(query.getInstanceName(), query.getInstanceStatus(), query.getAppId());
    	if(!StringUtils.isEmpty(query.getInstanceName()) || !StringUtils.isEmpty(query.getInstanceStatus())) {
    		return SysWorkflowTreeUtils.instanceTree(instances);
    	}else {
    		QueryWrapper<SysWorkflowInfo> workflowQuery = new QueryWrapper<SysWorkflowInfo>();
    		workflowQuery.lambda().eq(SysWorkflowInfo::getAppId, query.getAppId()).orderByAsc(SysWorkflowInfo::getWorkflowCode).orderByAsc(SysWorkflowInfo::getVersionNum);
        	List<SysWorkflowInfo> workflows = workflowMapper.selectList(workflowQuery);
        	return SysWorkflowTreeUtils.instanceTree(workflows, instances);
    	}
	}
	
	@Override
	@Transactional
	public boolean updateById(SysWorkflowInstance entity) {
		entity.setModifyTime(LocalDateTime.now());
		return super.updateById(entity);
	}
	
	@Override
	@Transactional
	public boolean removeById(Serializable id) {
		// 删除节点操作
		instanceNodeOperMapper.deleteByInstanceId(id);
		// 删除节点权限
		instanceNodeAuthMapper.deleteByInstanceId(id);
		// 删除节点
		instanceNodeMapper.deleteByInstanceId(id);
		return super.removeById(id);
	}

	@Override
	public MxGraphBean graph(String instanceId) {
		// 所有实例节点
    	List<SysWorkflowInstanceNode> nodes = instanceNodeMapper.queryNodesByInstanceId(instanceId);
    	MxGraphBean graph = null;
    	if(nodes.size()>0) {
        	List<SysWorkflowInstanceNodeOper> opers = instanceNodeOperMapper.queryNodeOpersByInstanceId(instanceId);
        	graph = WorkflowGraphEngine.graph(WorkflowEngine.nodes(nodes, opers));
    	}else {
    		 graph = new MxGraphBean();
    		 graph.setVertexs(new ArrayList<MxGraphVertexBean>(0));
    		 graph.setEdges(new ArrayList<MxGraphEdgeBean>(0));
    	}
		return graph;
	}
	
}
