package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNode;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:15:01
 */
@Data
public class SysWorkflowInstanceNodeQuery extends Page<SysWorkflowInstanceNode> {

        /**
     * 节点ID
     */
    private String nodeId;
        /**
     * 实例ID
     */
    private String instanceId;
        /**
     * 节点状态 0-未处理 1-通过 2-未通过 3-部分通过
     */
    private String nodeStatus;
        /**
     * 当前节点
     */
    private String activeFlag;
    
}
