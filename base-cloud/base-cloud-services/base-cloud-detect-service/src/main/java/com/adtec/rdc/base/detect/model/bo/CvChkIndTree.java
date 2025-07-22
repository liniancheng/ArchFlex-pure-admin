package com.adtec.rdc.base.crossvalidation.model.bo;

import lombok.Data;

import java.util.List;

/**
 * 指标树
 */
@Data
public class CvChkIndTree {
    /**
     * 指标编号
     */
    private String indNo;

    /**
     * 指标名
     */
    private String indNm;

    /**
     * 父级指标编号
     */
    private String parentNo;

    /**
     * 菜单排序
     */
    private String menuOrder;

    /**
     * 子节点
     */
    private List<CvChkIndTree> children;

    public void addChildren(CvChkIndTree tree) {
        this.children.add(tree);
    }
}
