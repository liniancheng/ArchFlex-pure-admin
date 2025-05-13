package com.adtec.rdc.base.knowledge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adtec.rdc.base.common.model.bo.TreeNode;
import com.adtec.rdc.base.knowledge.model.bo.KnowKnowledgeDirInfoTree;
import com.adtec.rdc.base.knowledge.model.po.KnowKnowledgeDirInfo;

/**
 * @author: JTao
 * @date: 2018/10/17 13:38
 * @description: 树形工具类
 */
public class TreeUtil {

    /**
     * 数组转树形结构
     * @param Knowledges
     * @param root
     * @return
     */
    public static List<KnowKnowledgeDirInfoTree> list2Tree(List<KnowKnowledgeDirInfo> Knowledges, String root){
        // 普通对象转树节点
        List<KnowKnowledgeDirInfoTree> list = buildTree(Knowledges);
        List<KnowKnowledgeDirInfoTree> trees = new ArrayList<>();
        for (KnowKnowledgeDirInfoTree tree: list) {
            if(root.equals(tree.getParentId())) {
                trees.add(tree);
            }
            for (KnowKnowledgeDirInfoTree t : list) {
                if (tree.getDirId().equals(t.getParentId())) {
                    if (tree.getChildren() == null) {
                        tree.setChildren(new ArrayList<KnowKnowledgeDirInfoTree>());
                    }
                    tree.addChildren(t);
                }
            }
        }
        return trees;
    }

    /**
     * 数组转树形结构
     * @param Knowledges
     * @param root
     * @return
     */
    public static List<TreeNode> list2TreeNode(List<KnowKnowledgeDirInfo> Knowledges, String root){
        // 普通对象转树节点
        Map<String, List<TreeNode>> treeMap = new HashMap<>();
        List<TreeNode> trees = new ArrayList<>();
        for (KnowKnowledgeDirInfo Knowledge: Knowledges) {
            List<TreeNode> children = treeMap.get(Knowledge.getParentId());
            if (children == null) {
                children = new ArrayList<>();
                treeMap.put(Knowledge.getParentId(), children);
            }
            TreeNode treeNode = copyKnowledgeInfo2TreeNode(Knowledge);
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
     * @param Knowledges
     * @return
     */
    public static List<KnowKnowledgeDirInfoTree> buildTree(List<KnowKnowledgeDirInfo> Knowledges){
        List<KnowKnowledgeDirInfoTree> trees = new ArrayList<>();
        Knowledges.forEach( knowledge->{
            trees.add(copyKnowledgeInfoToKnowledgeTree(knowledge));
        });
        return trees;
    }
    
    public static KnowKnowledgeDirInfo copyKnowledgeTreeToKnowledgeInfo(KnowKnowledgeDirInfoTree tree) {
    	KnowKnowledgeDirInfo knowledge = new KnowKnowledgeDirInfo();
    	knowledge.setDirId(tree.getDirId());
    	knowledge.setDirName(tree.getDirName());
    	knowledge.setDirOrder(tree.getDirOrder());
    	knowledge.setDirRmk(tree.getDirRmk());
    	knowledge.setParentId(tree.getParentId());
        return knowledge;
    }
    
    public static KnowKnowledgeDirInfoTree copyKnowledgeInfoToKnowledgeTree(KnowKnowledgeDirInfo knowledge) {
    	KnowKnowledgeDirInfoTree tree = new KnowKnowledgeDirInfoTree();
    	tree.setDirId(knowledge.getDirId());
    	tree.setDirName(knowledge.getDirName());
    	tree.setDirOrder(knowledge.getDirOrder());
    	tree.setDirRmk(knowledge.getDirRmk());
    	tree.setParentId(knowledge.getParentId());
        return tree;
    }
    
 
    public static  TreeNode copyKnowledgeInfo2TreeNode(KnowKnowledgeDirInfo Knowledge) {
        TreeNode treeNode = new TreeNode();
        treeNode.setKey(Knowledge.getDirId());
        treeNode.setTitle(Knowledge.getDirName());
        treeNode.setValue(Knowledge.getDirId());
        return treeNode;
    }
}
