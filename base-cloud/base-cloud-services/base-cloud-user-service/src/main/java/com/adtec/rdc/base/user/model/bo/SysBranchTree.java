package com.adtec.rdc.base.user.model.bo;

import java.util.List;

import lombok.Data;

/**
 * @author: liushp
 * @date: 2019/11/27
 * @description: 机构树
 */
@Data
public class SysBranchTree {

    /**
     * 子节点
     */
    private List<SysBranchTree> children;

    /**
     * 机构号
     */
    private String branchNo;

    /**
     * 机构名称
     */
    private String branchName;
    /**
     * 机构简称
     */
    private String branchShortName;
    /**
     * 机构类型
     */
    private String branchType;
    /**
     * 父级机构编号
     */
    private String parentBranchNo;
    /**
     * ETL同步
     */
    private String etFlag;
    /**
     * 是否是虚拟机构
     */
    private String virFlag;

    /**
     * 请求方式
     */
    private String method;

    public void addChildren(SysBranchTree tree) {
        this.children.add(tree);
    }

}
