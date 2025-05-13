package com.adtec.rdc.base.workflow.util;

import java.util.ArrayList;
import java.util.List;

import com.adtec.rdc.base.workflow.enums.WorkflowConstants;
import com.adtec.rdc.base.workflow.model.bo.SysWorkflowMacroTree;
import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;

/**
 * 工作流宏变量树工具类
 * @author JTao
 *
 */
public class SysWorkflowMacroTreeUtils {

	/**
	 * 按5种类型生成树
	 * @param macros
	 * @return
	 */
	public static List<SysWorkflowMacroTree> tree(List<SysWorkflowMacroInfo> macros) {
		List<SysWorkflowMacroTree> trees = new ArrayList<SysWorkflowMacroTree>(macros.size()+5);
		SysWorkflowMacroTree workflowTreeNode = getDirTreeNode(WorkflowConstants.WORKFLOW_MACRO_TYPE_WORKFLOW, "工作流宏变量");
		SysWorkflowMacroTree instanceTreeNode = getDirTreeNode(WorkflowConstants.WORKFLOW_MACRO_TYPE_INSTANCE, "工作流实例宏变量");
		SysWorkflowMacroTree nodeTreeNode = getDirTreeNode(WorkflowConstants.WORKFLOW_MACRO_TYPE_NODE, "工作流节点宏变量");
		SysWorkflowMacroTree authRoleTreeNode = getDirTreeNode(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_ROLE, "角色权限宏变量");
		SysWorkflowMacroTree authUserTreeNode = getDirTreeNode(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_USER, "用户权限宏变量");
		for(SysWorkflowMacroInfo macro : macros) {
			if(WorkflowConstants.WORKFLOW_MACRO_TYPE_WORKFLOW.equals(macro.getMacroType())) {
				if(workflowTreeNode.getChildren()==null) {
					workflowTreeNode.setChildren(new ArrayList<SysWorkflowMacroTree>());
				}
				workflowTreeNode.getChildren().add(getMacroTreeNode(macro));
			} else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_INSTANCE.equals(macro.getMacroType())) {
				if(instanceTreeNode.getChildren()==null) {
					instanceTreeNode.setChildren(new ArrayList<SysWorkflowMacroTree>());
				}
				instanceTreeNode.getChildren().add(getMacroTreeNode(macro));
			} else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_NODE.equals(macro.getMacroType())) {
				if(nodeTreeNode.getChildren()==null) {
					nodeTreeNode.setChildren(new ArrayList<SysWorkflowMacroTree>());
				}
				nodeTreeNode.getChildren().add(getMacroTreeNode(macro));
			} else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_ROLE.equals(macro.getMacroType())) {
				if(authRoleTreeNode.getChildren()==null) {
					authRoleTreeNode.setChildren(new ArrayList<SysWorkflowMacroTree>());
				}
				authRoleTreeNode.getChildren().add(getMacroTreeNode(macro));
			} else if(WorkflowConstants.WORKFLOW_MACRO_TYPE_AUTH_USER.equals(macro.getMacroType())) {
				if(authUserTreeNode.getChildren()==null) {
					authUserTreeNode.setChildren(new ArrayList<SysWorkflowMacroTree>());
				}
				authUserTreeNode.getChildren().add(getMacroTreeNode(macro));
			}
		}
		trees.add(workflowTreeNode);
		trees.add(instanceTreeNode);
		trees.add(nodeTreeNode);
		trees.add(authRoleTreeNode);
		trees.add(authUserTreeNode);
		return trees;
	}

	private static SysWorkflowMacroTree getMacroTreeNode(SysWorkflowMacroInfo macro) {
		SysWorkflowMacroTree tree = new SysWorkflowMacroTree();
		tree.setMacroId(macro.getMacroId());
		tree.setMacroName(macro.getMacroName());
		tree.setMacroCode(macro.getMacroCode());
		tree.setMacroType("macro");
		tree.setMacroRmk(macro.getMacroRmk());
		return tree;
	}

	private static SysWorkflowMacroTree getDirTreeNode(String macroType, String macroName) {
		SysWorkflowMacroTree tree = new SysWorkflowMacroTree();
		tree.setMacroId("MACRO_DIR_"+macroType);
		tree.setMacroName(macroName);
		tree.setMacroCode("");
		tree.setMacroType(macroType);
		tree.setMacroRmk("");
		return tree;
	}

}
