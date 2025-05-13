package com.adtec.rdc.base.workflow.model.bo;

import java.util.List;

import lombok.Data;

@Data
public class SysWorkflowTree {
	/**
     * 子节点
     */
    private List<SysWorkflowTree> children;
    
    /**
     * 流程ID
     */
    private String workflowId;
    /**
     * 流程名称
     */
    private String workflowName;
    /**
     * 流程类型
     */
    private String workflowType;
    /**
     * 流程描述
     */
    private String workflowRmk;
    /**
     * 流程状态
     */
    private String workflowStatus;
    /**
     * 上级ID
     */
    private String parentId;
    /**
     * 最后版本
     */
    private String versionMax;
}
