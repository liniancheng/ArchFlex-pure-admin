package com.adtec.rdc.base.tenant.model.bo;

import java.util.List;

import lombok.Data;

/**
 * 租户树
 */
@Data
public class TreeVo {

    private String key;
    
    private String value;
   
    /**
     * 业务
     */
    private List<TreeVo> children;
    
    public void addChildren(TreeVo tree) {
        this.children.add(tree);
    }
}
