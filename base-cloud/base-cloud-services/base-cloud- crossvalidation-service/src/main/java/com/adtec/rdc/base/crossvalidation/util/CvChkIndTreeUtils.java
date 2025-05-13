package com.adtec.rdc.base.crossvalidation.util;

import com.adtec.rdc.base.crossvalidation.model.bo.CvChkIndTree;
import com.adtec.rdc.base.crossvalidation.model.po.CvChkInd;
import com.adtec.rdc.base.common.model.bo.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CvChkIndTreeUtils {

	public static CvChkIndTree copyIndInfoToIndTree(CvChkInd cvChkInd){
	    CvChkIndTree cvChkIndTree = new CvChkIndTree();
	    cvChkIndTree.setIndNo(cvChkInd.getIndNo());
	    cvChkIndTree.setIndNm(cvChkInd.getIndNm());
	    cvChkIndTree.setParentNo(cvChkInd.getParentNo());
	    cvChkIndTree.setMenuOrder(cvChkInd.getMenuOrder());
	    return cvChkIndTree;
	}

	/**
	 * 对象转树节点
	 * @param cvChkIndList
	 * @return
	 */
	public static List<CvChkIndTree> buildTree(List<CvChkInd> cvChkIndList){
		List<CvChkIndTree> cvChkIndTreeList = new ArrayList<>();
		cvChkIndList.forEach(cvChkInd -> {
			cvChkIndTreeList.add(copyIndInfoToIndTree(cvChkInd));
		});
		return cvChkIndTreeList;
	}

	/**
	 * 数组转树结构
	 */
	public static List<CvChkIndTree> list2Tree(List<CvChkInd> cvChkIndList,String root){
		List<CvChkIndTree> cvChkIndTreeList = buildTree(cvChkIndList);
		List<CvChkIndTree> trees = new ArrayList<>();
		for (CvChkIndTree tree : cvChkIndTreeList) {
			if (root.equals(tree.getParentNo())){
				trees.add(tree);
			}
			for (CvChkIndTree cvChkIndTree : cvChkIndTreeList) {
				if (tree.getIndNo().equals(cvChkIndTree.getParentNo())){
					if (tree.getChildren() == null){
						tree.setChildren(new ArrayList<CvChkIndTree>());
					}
					tree.addChildren(cvChkIndTree);
				}
			}
		}
		return trees;
	}

	/**
	 * 对象转树节点
	 */
	public static TreeNode copyIndInfoToTreeNode(CvChkInd cvChkInd){
	    TreeNode treeNode = new TreeNode();
	    treeNode.setKey(cvChkInd.getIndNo());
	    treeNode.setTitle(cvChkInd.getIndNm());
	    treeNode.setValue(cvChkInd.getIndNo());
	    treeNode.setParentId(cvChkInd.getParentNo());
	    return treeNode;
	}
	/**
	 * 数组转树形节点
	 */
	public static List<TreeNode> list2TreeNode(List<CvChkInd> cvChkIndList,String root){
		Map<String, List<TreeNode>> map = new HashMap<>();
		List<TreeNode> treeNodes = new ArrayList<>();
		for (CvChkInd cvChkInd : cvChkIndList) {
			List<TreeNode> children = map.get(cvChkInd.getParentNo());
			if (children == null){
				children = new ArrayList<>();
				map.put(cvChkInd.getParentNo(),children);
			}
			TreeNode treeNode = copyIndInfoToTreeNode(cvChkInd);
			children.add(treeNode);
			treeNodes.add(treeNode);
		}
		for (TreeNode treeNode : treeNodes) {
			treeNode.setChildren(map.get(treeNode.getKey()));
		}
		return map.get(root);
	}

	public static List<CvChkIndTree> setChildren(List<CvChkIndTree> trees,List<CvChkInd> inds){
		List<CvChkIndTree> list = buildTree(inds);
		for (CvChkIndTree tree : trees) {
			for (CvChkIndTree cvChkIndTree : list) {
				if (tree.getParentNo().equals(cvChkIndTree.getParentNo())){
					if (tree.getChildren() == null){
						tree.setChildren(new ArrayList<CvChkIndTree>());
					}
					tree.addChildren(cvChkIndTree);
				}
			}
		}
		return trees;
	}
}
