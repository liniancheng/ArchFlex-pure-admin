package com.adtec.rdc.base.workflow.engine;

import java.util.List;

import lombok.Data;

/**
 * 流程节点
 * @author JTao
 *
 */
@Data
public class WorkflowNode {
	/**
     * 节点ID
     */
    private String nodeId;

    /**
     * 节点状态             
     */
    private String nodeStatus;
    
    /**
     * 节点名称
     */
    private String nodeName;
    
    /**
     * 上级节点ID
     */
    private String parentId;
    
    /**
     * 审批列表
     */
    private List<WorkflowNodeOper> opers;
    
    /**
     * 通过审批数
     */
    private int agreeNum;
    
    /**
     * 不通过审批数
     */
    private int disagreeNum;
}
