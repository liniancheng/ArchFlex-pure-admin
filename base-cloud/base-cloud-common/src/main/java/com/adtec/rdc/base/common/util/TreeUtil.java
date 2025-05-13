package com.adtec.rdc.base.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adtec.rdc.base.common.model.bo.TreeNode;
/**
 * 树形工具类
 * @author: dengchf     
 * @date:   2020年6月15日 
 * @Description:
 * @version V1.0 
 * @Copyright: adtec
 */
public class TreeUtil {
	/**
	 * 1.数组转树形结构
	 */
	public static List<TreeNode> conver2Tree(List<TreeNode> list, String root) {
		// 普通对象转树节点
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (TreeNode node : list) {
			if (root.equals(node.getParentId())) {
				tree.add(addChildren(node, list));
			}
		}
		return tree;
	}

	/**
	 * 2.递归查询子节点
	 */
	private static TreeNode addChildren(TreeNode tree, List<TreeNode> list) {
		for (TreeNode node : list) {
			if (tree.getKey().equals(node.getParentId())) {
				if(tree.getChildren() == null) {
					tree.setChildren(new ArrayList<TreeNode>());
				}
				tree.getChildren().add(addChildren(node, list));
			}
		}
		return tree;
	}
	
	public static List<TreeNode> conver2Tree(List<TreeNode> treeNodes){
		List<TreeNode> tree = new ArrayList<TreeNode>();
		Map<String, TreeNode> map = new HashMap<String, TreeNode>();
		
		Optional.ofNullable(treeNodes).ifPresent(trees->{
			trees.forEach(node ->{
				map.put(node.getKey(), node);
			});
			trees.forEach(node ->{
				TreeNode parent = map.get(node.getParentId());
				if(parent == null) {
					tree.add(node);
				}else {
					if(parent.getChildren() == null) {
						parent.setChildren(new ArrayList<TreeNode>());
					}
					parent.getChildren().add(node);
				}
			});
		});
		return tree;
	}

}
