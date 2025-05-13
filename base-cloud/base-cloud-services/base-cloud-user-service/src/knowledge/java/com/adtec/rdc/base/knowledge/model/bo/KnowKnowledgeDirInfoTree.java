package com.adtec.rdc.base.knowledge.model.bo;

import java.util.List;

import lombok.Data;

/**
 * <p>
 * 知识分类表
 * </p>
 *
 * @author xinglj
 * @date 2020-06-17 09:38:38
 */
@Data
public class KnowKnowledgeDirInfoTree{
    /**
     * 子节点
     */
    private List<KnowKnowledgeDirInfoTree> children;

    /**
     * 分类ID
     */
    private String dirId;

    /**
     * 分类名称
     */
    private String dirName;
    /**
     * 父ID
     */
    private String parentId;
    /**
     * 分类描述
     */
    private String dirRmk;
    /**
     * 排序
     */
    private Integer dirOrder;
    
    public void addChildren(KnowKnowledgeDirInfoTree tree) {
        this.children.add(tree);
    }

}