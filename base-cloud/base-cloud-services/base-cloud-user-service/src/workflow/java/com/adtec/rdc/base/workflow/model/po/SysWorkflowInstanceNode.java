package com.adtec.rdc.base.workflow.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 工作流实例节点信息 
 * </p>
 *
 * @author adtec
 * @date 2020-06-30 09:15:01
 */
@Data
@Accessors(chain = true)
@TableName(value = "SYS_WORKFLOW_INST_NODE")
public class SysWorkflowInstanceNode implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 节点ID
     */
    @TableId(value = "INODE_ID", type = IdType.UUID)
    private String inodeId;

    /**
     * 节点ID
     */
    @TableId(value = "NODE_ID", type = IdType.UUID)
    private String nodeId;

    /**
     * 实例ID
     */
    @TableField(value = "INSTANCE_ID", strategy = FieldStrategy.IGNORED) 
    private String instanceId;
    
    /**
     * 节点状态 0-未处理 1-通过 2-未通过 3-部分通过
     */
    @TableField(value = "NODE_STATUS", strategy = FieldStrategy.IGNORED) 
    private String nodeStatus;
    
    /**
     * 当前节点
     */
    @TableField(value = "ACTIVE_FLAG", strategy = FieldStrategy.IGNORED) 
    private String activeFlag;
            
    /**
     * 以下为流程节点属性
     */
    @TableField(exist = false)
    private String nodeName;
    @TableField(exist = false)
    private String nodeRmk;
    @TableField(exist = false)
    private String parentId;
    @TableField(exist = false)
    private int agreeNum;
    @TableField(exist = false)
    private int disagreeNum;
    @TableField(exist = false)
    private int nodeLevel;
}