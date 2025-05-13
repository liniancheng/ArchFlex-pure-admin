package com.adtec.rdc.base.user.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.user.model.bo.SysBranchTree;
import com.adtec.rdc.base.user.model.po.SysBranchInfo;

/**
 * @author: JTao
 * @date: 2018/10/17 13:38
 * @description: 树形工具类
 */
public class TreeUtilForBranch {

	/**
	 * 数组转树形结构
	 * @param Branchs
	 * @param root
	 * @return
	 */
	public static List<SysBranchTree> list2Tree(List<SysBranchInfo> branchs, String root){
		// 普通对象转树节点
		List<SysBranchTree> list = buildTree(branchs);
		List<SysBranchTree> trees = new ArrayList<>();
		for (SysBranchTree tree: list) {
			if(root.equals(tree.getParentBranchNo())) {
				trees.add(tree);
			}
			for (SysBranchTree t : list) {
				if (tree.getBranchNo().equals(t.getParentBranchNo())) {
					if (tree.getChildren() == null) {
						tree.setChildren(new ArrayList<SysBranchTree>());
					}
					tree.addChildren(t);
				}
			}
		}
		return trees;
	}

	/**
	 * 数组转树形结构
	 * @param Branchs
	 * @param root
	 * @return
	 */
	public static List<TreeNode> list2TreeNode(List<SysBranchInfo> branchs, String root){
		// 普通对象转树节点
		Map<String, List<TreeNode>> treeMap = new HashMap<>();
		List<TreeNode> trees = new ArrayList<>();
		for (SysBranchInfo branch: branchs) {
			List<TreeNode> children = treeMap.get(branch.getParentBranchNo());
			if (children == null) {
				children = new ArrayList<>();
				treeMap.put(branch.getParentBranchNo(), children);
			}
			TreeNode treeNode = copyBranchInfo2TreeNode(branch);
			children.add(treeNode);
			trees.add(treeNode);
		}
		for (TreeNode tree: trees) {
			tree.setChildren(treeMap.get(tree.getKey()));
		}
		return treeMap.get(root);
	}

	/**
	 * 对象转树节点
	 * @param Branchs
	 * @return
	 */
	public static List<SysBranchTree> buildTree(List<SysBranchInfo> branchs){
		List<SysBranchTree> trees = new ArrayList<>();
		branchs.forEach( branch->{
			trees.add(copyBranchInfoToBranchTree(branch));
		});
		return trees;
	}

	public static SysBranchInfo copyBranchTreeToBranchInfo(SysBranchTree tree) {
		SysBranchInfo branch = new SysBranchInfo();
		branch.setBranchNo(tree.getBranchNo());
		branch.setBranchName(tree.getBranchName());
		branch.setBranchShortName(tree.getBranchShortName());
		branch.setBranchType(tree.getBranchType());
		branch.setEtFlag(tree.getEtFlag());
		branch.setParentBranchNo(tree.getParentBranchNo());
		branch.setVirFlag(tree.getVirFlag());
		return branch;
	}

	public static SysBranchTree copyBranchInfoToBranchTree(SysBranchInfo branch) {
		SysBranchTree tree = new SysBranchTree();
		tree.setBranchNo(branch.getBranchNo());
		tree.setBranchName(branch.getBranchName());
		tree.setBranchShortName(branch.getBranchShortName());
		tree.setBranchType(branch.getBranchType());
		tree.setEtFlag(branch.getEtFlag());
		tree.setParentBranchNo(branch.getParentBranchNo());
		tree.setVirFlag(branch.getVirFlag());
		return tree;
	}

	public static  TreeNode copyBranchInfo2TreeNode(SysBranchInfo branch) {
		TreeNode treeNode = new TreeNode();
		treeNode.setKey(branch.getBranchNo());
		treeNode.setTitle(branch.getBranchName());
		treeNode.setValue(branch.getBranchNo());
		return treeNode;
	}

	public static List<SysBranchTree> setChildren(List<SysBranchTree> branchs, List<SysBranchInfo> allBranch){
		List<SysBranchTree> list = buildTree(allBranch);
		for (SysBranchTree b : branchs) {
			for (SysBranchTree t : list) {
				if (b.getBranchNo().equals(t.getParentBranchNo())) {
					if (b.getChildren() == null) {
						b.setChildren(new ArrayList<SysBranchTree>());
					}
					b.addChildren(t);
				}
			}
		}
		return branchs;
	}
}
