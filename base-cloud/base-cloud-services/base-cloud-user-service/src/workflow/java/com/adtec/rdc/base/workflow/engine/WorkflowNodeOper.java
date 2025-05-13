package com.adtec.rdc.base.workflow.engine;

import lombok.Data;

/**
 * 流程节点审批
 * @author JTao
 *
 */
@Data
public class WorkflowNodeOper {
	/**
     * 节点ID
     */
    private String inodeId;
    
	/**
     * 审批用户登录名
     */
    private String loginName;

    /**
     * 审批用户姓名           
     */
    private String userName;
    
    /**
     * 审批时间
     */
    private String operTime;
    
    /**
     * 审批状态
     */
    private String operStatus;
    
    /**
     * 审批说明
     */
    private String operRmk;
}
