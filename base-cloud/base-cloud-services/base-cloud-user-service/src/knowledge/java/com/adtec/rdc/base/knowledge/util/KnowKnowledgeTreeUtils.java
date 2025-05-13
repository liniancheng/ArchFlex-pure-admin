package com.adtec.rdc.base.knowledge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeInfo;
import com.adtec.rdc.web.antd.bo.VueTreeNode;
import com.alibaba.druid.util.StringUtils;

public class KnowKnowledgeTreeUtils {
	private static final String KNOW_VIEW_URL = "/know/view/";
	private static final String KNOW_VIEW_COMPONENT = "/admin/knowledge/bus";

	public static List<VueTreeNode> tree(List<KnowKnowledgeDirInfo> dirs, List<KnowKnowledgeInfo> knows, String dirId) {
		Map<String, List<VueTreeNode>> treeMap = new HashMap<String, List<VueTreeNode>>();
		List<VueTreeNode> trees = new ArrayList<VueTreeNode>();
		if(dirs != null) {
			for(KnowKnowledgeDirInfo dir : dirs) {
				VueTreeNode tree = getDirTreeNode(dir);
				if(!treeMap.containsKey(dir.getParentId())) {
					treeMap.put(dir.getParentId(), new ArrayList<VueTreeNode>());
				}
				treeMap.get(dir.getParentId()).add(tree);
				trees.add(tree);
			}
		}
		if(knows != null) {
			for(KnowKnowledgeInfo know : knows) {
				VueTreeNode tree = getKnowTreeNode(know);
				if(!treeMap.containsKey(know.getDirId())) {
					treeMap.put(know.getDirId(), new ArrayList<VueTreeNode>());
				}
				treeMap.get(know.getDirId()).add(tree);
				trees.add(tree);
			}
		}
		for(VueTreeNode tree : trees) {
			if(treeMap.containsKey(tree.getKey())) {
				tree.setChildren(treeMap.get(tree.getKey()));
			}
		}
		return treeMap.get(dirId);
	}

	private static VueTreeNode getKnowTreeNode(KnowKnowledgeInfo know) {
		VueTreeNode tree = new VueTreeNode();
		tree.setKey(know.getKnowledgeId());
		tree.setValue(know.getKnowledgeId());
		tree.setTitle(know.getKnowledgeTitle());
		tree.setSelectable(true);
		tree.setIsLeaf(true);
		tree.setUrl(KNOW_VIEW_URL);
		tree.setComponent(KNOW_VIEW_COMPONENT);
		return tree;
	}

	private static VueTreeNode getDirTreeNode(KnowKnowledgeDirInfo dir) {
		VueTreeNode tree = new VueTreeNode();
		tree.setKey(dir.getDirId());
		tree.setValue(dir.getDirId());
		tree.setTitle(dir.getDirName());
		tree.setSelectable(true);
		tree.setIsLeaf(false);
		tree.setUrl(KNOW_VIEW_URL);
		tree.setComponent(KNOW_VIEW_COMPONENT);
		return tree;
	}

}
