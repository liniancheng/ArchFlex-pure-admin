package com.adtec.rdc.base.workflow.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.common.util.DateUtil;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeOper;

/**
 * 流程引擎
 * @author JTao
 *
 */
public class WorkflowEngine {
	// 节点状态 - 完成/进行中/未开始/回退
	private static final String NODE_STATUS_DONE = "done";
	private static final String NODE_STATUS_DOING = "ding";
	private static final String NODE_STATUS_UNDO = "undo";
	private static final String NODE_STATUS_BACK = "back";
	
	/**
	 * 转换成流程节点
	 * @param instanceNodes
	 * @param instanceNodeOpers
	 * @return
	 */
	public static List<WorkflowNode> nodes(List<SysWorkflowInstanceNode> instanceNodes, List<SysWorkflowInstanceNodeOper> instanceNodeOpers) {
		List<WorkflowNode> nodes = new ArrayList<WorkflowNode>(instanceNodes.size());
		Map<String, List<WorkflowNodeOper>> operMap = new HashMap<String, List<WorkflowNodeOper>>(instanceNodes.size());
		for(SysWorkflowInstanceNodeOper instanceNodeOper : instanceNodeOpers) {
			WorkflowNodeOper oper = getWorkflowNodeOper(instanceNodeOper);
			if(!operMap.containsKey(oper.getInodeId())) {
				operMap.put(oper.getInodeId(), new ArrayList<WorkflowNodeOper>());
			}
			operMap.get(oper.getInodeId()).add(oper);
		}
		for(SysWorkflowInstanceNode instanceNode : instanceNodes) {
			WorkflowNode node = getWorkflowNode(instanceNode);
			node.setOpers(operMap.get(node.getNodeId()));
			setWorkflowNodeStatus(node);
			nodes.add(node);
		}
		return nodes;
	}

	/**
	 * 设置节点状态
	 * @param node
	 */
	private static void setWorkflowNodeStatus(WorkflowNode node) {
		List<WorkflowNodeOper> opers = node.getOpers();
		if(opers != null && opers.size() > 0) {
			int agreeNum = 0;
			int disagreeNum = 0;
			node.setNodeStatus(NODE_STATUS_DOING);
			for(WorkflowNodeOper oper : opers) {
				if("1".equals(oper.getOperStatus())) {
					agreeNum++;
				}else {
					disagreeNum++;
				}
				if(agreeNum>=node.getAgreeNum()) {
					node.setNodeStatus(NODE_STATUS_DONE);
					break;
				}
				if(disagreeNum>=node.getDisagreeNum()) {
					node.setNodeStatus(NODE_STATUS_BACK);
					break;
				}
			}
		}
	}
	
	private static WorkflowNode getWorkflowNode(SysWorkflowInstanceNode instanceNode) {
		WorkflowNode node = new WorkflowNode();
		node.setNodeId(instanceNode.getNodeId());
		node.setNodeName(instanceNode.getNodeName());
		node.setParentId(instanceNode.getParentId());
		node.setAgreeNum(instanceNode.getAgreeNum());
		node.setDisagreeNum(instanceNode.getDisagreeNum());
		if("1".equals(instanceNode.getActiveFlag())) {
			node.setNodeStatus(NODE_STATUS_DOING);
		}else {
			node.setNodeStatus(NODE_STATUS_UNDO);
		}
		return node;
	}

	private static WorkflowNodeOper getWorkflowNodeOper(SysWorkflowInstanceNodeOper instanceNodeOper) {
		WorkflowNodeOper oper = new WorkflowNodeOper();
		oper.setInodeId(instanceNodeOper.getInodeId());
		oper.setLoginName(instanceNodeOper.getLoginName());
		oper.setUserName(instanceNodeOper.getUserName());
		oper.setOperTime(DateUtil.format(instanceNodeOper.getOperTime(), "yyyy-MM-dd HH:mm:ss"));
		oper.setOperStatus(instanceNodeOper.getAgreeFlag());
		oper.setOperRmk(instanceNodeOper.getOperRmk());
		return oper;
	}

}