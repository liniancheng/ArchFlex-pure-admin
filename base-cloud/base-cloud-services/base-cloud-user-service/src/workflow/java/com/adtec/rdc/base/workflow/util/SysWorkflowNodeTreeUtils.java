package com.adtec.rdc.base.workflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.workflow.model.bo.SysWorkflowNodeTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowNodeInfo;

public class SysWorkflowNodeTreeUtils {

	public static List<SysWorkflowNodeTree> tree(List<SysWorkflowNodeInfo> nodes) {
		Map<String, List<SysWorkflowNodeTree>> treeMap = new HashMap<String, List<SysWorkflowNodeTree>>();
		for(SysWorkflowNodeInfo node : nodes) {
			String parentIds = node.getParentIds();
			if(parentIds.endsWith(",")) {
				parentIds = parentIds.substring(0, parentIds.length()-1);
			}
			String[] arrayParentId = parentIds.split(",");
			for(String parentId : arrayParentId) {
				SysWorkflowNodeTree tree = getWorkflowNodeTreeNode(parentId, node);
				if(!treeMap.containsKey(parentId)) {
					treeMap.put(parentId, new ArrayList<SysWorkflowNodeTree>());
				}
				treeMap.get(parentId).add(tree);
			}
		}
		for(List<SysWorkflowNodeTree> trees : treeMap.values()) {
			for(SysWorkflowNodeTree tree : trees) {
				tree.setChildren(treeMap.get(tree.getTrueNodeId()));
			}
		}
		List<SysWorkflowNodeTree> trees = new ArrayList<SysWorkflowNodeTree>(1);
		SysWorkflowNodeTree root = getRootTreeNode();
		root.setChildren(treeMap.get("-1"));
		trees.add(root);
		return trees;
	}

	private static SysWorkflowNodeTree getWorkflowNodeTreeNode(String parentId, SysWorkflowNodeInfo node) {
		SysWorkflowNodeTree tree = new SysWorkflowNodeTree();
		tree.setNodeId(parentId + "." + node.getWfnodeId());
		tree.setNodeName(node.getWfnodeName());
		tree.setNodeRmk(node.getWfnodeRmk());
		tree.setNodeLevel(node.getWfnodeLevel());
		tree.setTrueNodeId(node.getWfnodeId());
		return tree;
	}

	private static SysWorkflowNodeTree getRootTreeNode() {
		SysWorkflowNodeTree tree = new SysWorkflowNodeTree();
		tree.setNodeId("-1");
		tree.setNodeName("开始");
		tree.setNodeRmk("流程开始节点");
		tree.setNodeLevel(0);
		tree.setTrueNodeId("-1");
		return tree;
	}

	public static List<SysWorkflowNodeTree> parentNodeTree(List<SysWorkflowNodeInfo> nodes) {
		List<SysWorkflowNodeTree> trees = new ArrayList<SysWorkflowNodeTree>(nodes.size());
		for(SysWorkflowNodeInfo node : nodes) {
			trees.add(getWorkflowNodeTreeNode(node));
		}
		return trees;
	}

	private static SysWorkflowNodeTree getWorkflowNodeTreeNode(SysWorkflowNodeInfo node) {
		SysWorkflowNodeTree tree = new SysWorkflowNodeTree();
		tree.setNodeId(node.getWfnodeId());
		tree.setNodeName(node.getWfnodeName());
		tree.setNodeRmk(node.getWfnodeRmk());
		tree.setNodeLevel(node.getWfnodeLevel());
		tree.setTrueNodeId(node.getWfnodeId());
		return tree;
	}

}
