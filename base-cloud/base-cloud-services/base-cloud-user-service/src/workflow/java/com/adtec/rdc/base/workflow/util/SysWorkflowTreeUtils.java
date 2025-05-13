package com.adtec.rdc.base.workflow.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.workflow.model.bo.SysWorkflowTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInfo;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;

public class SysWorkflowTreeUtils {

	public static List<SysWorkflowTree> tree(List<SysWorkflowInfo> workflows) {
		List<SysWorkflowTree> trees = new ArrayList<SysWorkflowTree>(workflows.size());
		for(SysWorkflowInfo workflow : workflows) {
			trees.add(getWorkflowTreeNode(workflow));
		}
		return trees;
	}

	private static SysWorkflowTree getWorkflowTreeNode(SysWorkflowInfo workflow) {
		SysWorkflowTree tree = new SysWorkflowTree();
		tree.setWorkflowId(workflow.getWorkflowId());
		tree.setWorkflowName(workflow.getWorkflowName()+"-"+workflow.getVersionNum());
		tree.setWorkflowRmk(workflow.getWorkflowRmk());
		tree.setWorkflowType("workflow");
		tree.setVersionMax(workflow.getVersionMax());
		return tree;
	}

	public static List<SysWorkflowTree> tree(List<SysWorkflowTypeInfo> types, List<SysWorkflowInfo> workflows) {
		Map<String, List<SysWorkflowTree>> treeMap = new HashMap<String, List<SysWorkflowTree>>();
		for(SysWorkflowInfo workflow : workflows) {
			SysWorkflowTree tree = getWorkflowTreeNode(workflow);
			if(!treeMap.containsKey(workflow.getTypeId())) {
				treeMap.put(workflow.getTypeId(), new ArrayList<SysWorkflowTree>());
			}
			treeMap.get(workflow.getTypeId()).add(tree);
		}
		List<SysWorkflowTree> trees = new ArrayList<SysWorkflowTree>(types.size());
		for(SysWorkflowTypeInfo type : types) {
			SysWorkflowTree tree = getWorkflowTypeTreeNode(type);
			tree.setChildren(treeMap.get(type.getTypeId()));
			trees.add(tree);
		}
		return trees;
	}

	private static SysWorkflowTree getWorkflowTypeTreeNode(SysWorkflowTypeInfo type) {
		SysWorkflowTree tree = new SysWorkflowTree();
		tree.setWorkflowId(type.getTypeId());
		tree.setWorkflowName(type.getTypeName());
		tree.setWorkflowRmk(type.getTypeRmk());
		tree.setWorkflowType("type");
		return tree;
	}

	public static List<SysWorkflowTree> instanceTree(List<SysWorkflowInstance> instances) {
		List<SysWorkflowTree> trees = new ArrayList<SysWorkflowTree>(instances.size());
		for(SysWorkflowInstance instance : instances) {
			trees.add(getInstanceTreeNode(instance));
		}
		return trees;
	}

	private static SysWorkflowTree getInstanceTreeNode(SysWorkflowInstance instance) {
		SysWorkflowTree tree = new SysWorkflowTree();
		tree.setWorkflowId(instance.getInstanceId());
		tree.setWorkflowName(instance.getInstanceName());
		tree.setWorkflowRmk(instance.getInstanceRmk());
		tree.setWorkflowStatus(instance.getInstanceStatus());
		tree.setWorkflowType("instance");
		return tree;
	}

	public static List<SysWorkflowTree> instanceTree(List<SysWorkflowInfo> workflows,
			List<SysWorkflowInstance> instances) {
		Map<String, List<SysWorkflowTree>> treeMap = new HashMap<String, List<SysWorkflowTree>>();
		for(SysWorkflowInstance instance : instances) {
			SysWorkflowTree tree = getInstanceTreeNode(instance);
			if(!treeMap.containsKey(instance.getWorkflowId())) {
				treeMap.put(instance.getWorkflowId(), new ArrayList<SysWorkflowTree>());
			}
			treeMap.get(instance.getWorkflowId()).add(tree);
		}
		List<SysWorkflowTree> trees = new ArrayList<SysWorkflowTree>(workflows.size());
		for(SysWorkflowInfo workflow : workflows) {
			SysWorkflowTree tree = getWorkflowTreeNode(workflow);
			tree.setChildren(treeMap.get(workflow.getWorkflowId()));
			trees.add(tree);
		}
		return trees;
	}

}
