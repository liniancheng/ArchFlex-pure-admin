package com.adtec.rdc.base.workflow.model.bo;

import java.util.List;

import lombok.Data;

@Data
public class SysWorkflowNodeTree {
	/**
     * 子节点
     */
    private List<SysWorkflowNodeTree> children;
    /**
     * 节点ID
     */
    private String nodeId;
    /**
     * 节点名称
     */
    private String nodeName;
    /**
     * 节点描述
     */
    private String nodeRmk;
    /**
     * 上级ID
     */
    private String parentId;
    /**
     * 节点层级
     */
    private int nodeLevel;
    
    /**
     * 真实节点ID
     */
    private String trueNodeId;
}
