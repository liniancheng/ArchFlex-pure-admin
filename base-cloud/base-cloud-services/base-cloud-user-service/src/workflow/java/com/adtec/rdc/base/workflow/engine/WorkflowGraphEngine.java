package com.adtec.rdc.base.workflow.engine;

import java.util.ArrayList;
import java.util.List;

import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphBean;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphEdgeBean;
import com.adtec.rdc.base.common.model.bo.mxgraph.MxGraphVertexBean;
import com.alibaba.druid.util.StringUtils;

/**
 * 流程图引擎
 * @author JTao
 *
 */
public class WorkflowGraphEngine {

	public static MxGraphBean graph(List<WorkflowNode> workflowNodes) {
		MxGraphBean graph = new MxGraphBean();
		List<MxGraphVertexBean> nodes = new ArrayList<MxGraphVertexBean>(workflowNodes.size()+1);
		nodes.add(getStartNode());
		List<MxGraphEdgeBean> edges = new ArrayList<MxGraphEdgeBean>(workflowNodes.size());
		for(WorkflowNode workflowNode : workflowNodes) {
			nodes.add(getNode(workflowNode));
			addEdges(edges, workflowNode);
		}
		graph.setVertexs(nodes);
		graph.setEdges(edges);
		return graph;
	}

	private static void addEdges(List<MxGraphEdgeBean> edges, WorkflowNode workflowNode) {
		String[] parentIds = workflowNode.getParentId().split(",");
		for(String parentId : parentIds) {
			if(StringUtils.isEmpty(parentId)) {
				continue;
			}
			MxGraphEdgeBean edge = new MxGraphEdgeBean();
			edge.setSourceVertexId(parentId);
			edge.setTargetVertexId(workflowNode.getNodeId());
			edge.setTitle("");
			edges.add(edge);
		}
	}
	
	private static MxGraphVertexBean getStartNode() {
		MxGraphVertexBean node = new MxGraphVertexBean();
		node.setVertexId("-1");
		node.setVertexName("开始");
		node.setVertexStatus("done");
		return node;
	}

	private static MxGraphVertexBean getNode(WorkflowNode workflowNode) {
		MxGraphVertexBean node = new MxGraphVertexBean();
		node.setVertexId(workflowNode.getNodeId());
		node.setVertexName(workflowNode.getNodeName());
		node.setVertexStatus(workflowNode.getNodeStatus());
		return node;
	}

}
