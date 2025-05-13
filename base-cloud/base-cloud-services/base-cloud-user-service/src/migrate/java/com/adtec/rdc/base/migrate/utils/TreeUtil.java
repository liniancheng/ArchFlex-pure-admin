package com.adtec.rdc.base.migrate.utils;

import java.util.ArrayList;
import java.util.List;

import com.adtec.rdc.base.migrate.model.bo.SysMigrateTree;
import com.adtec.rdc.base.migrate.model.po.SysMigrateInfo;

public class TreeUtil {

	/**
	 * list转tree
	 * @param list
	 * @param treeRoot
	 * @return
	 */
	public static List<SysMigrateTree> listToTree(List<SysMigrateInfo> list, String treeRoot) {

		List<SysMigrateTree> nodes = converToTreeNodes(list);
		List<SysMigrateTree> trees = new ArrayList<SysMigrateTree>();
		for (SysMigrateTree node : nodes) {
			if(treeRoot.equals(node.getParentMigId())) {
				trees.add(addChildren(node, nodes));
			}
		}
		return trees;
	}
	/**
	 * 递归查询子节点
	 * @param tree
	 * @param treeNodes
	 * @return
	 */
	private static SysMigrateTree addChildren(SysMigrateTree tree,List<SysMigrateTree> treeNodes) {
		for (SysMigrateTree node : treeNodes) {
			if(tree.getMigId().equals(node.getParentMigId())) {
				if(tree.getChildren() == null) {
					tree.setChildren(new ArrayList<SysMigrateTree>());
				}
				tree.getChildren().add(addChildren(node, treeNodes));
			}
		}
		return tree;
	}
	
	
	/**
	 * 实体对象转treeNode
	 * @param migrateInfos
	 * @return
	 */
	public static List<SysMigrateTree> converToTreeNodes(List<SysMigrateInfo> migrateInfos){
		List<SysMigrateTree> trees = new ArrayList<SysMigrateTree>();
		migrateInfos.forEach(info ->{
			trees.add(new SysMigrateTree(info));
		});
		return trees;
	}
	
}
