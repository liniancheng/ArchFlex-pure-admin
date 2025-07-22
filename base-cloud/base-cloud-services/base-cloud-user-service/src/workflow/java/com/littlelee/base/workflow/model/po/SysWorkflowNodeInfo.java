package com.littlelee.base.workflow.model.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 工作流节点信息 
 * </p>
 *
 * @author littlelee
 * @date 2020-06-30 09:14:21
 */
@Data
@Accessors(chain = true)
public class SysWorkflowNodeInfo implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    @TableId(value = "NODE_ID", type = IdType.ASSIGN_UUID)
    private String wfnodeId;

    /**
     * 节点名称
     */
    @TableField(value = "NODE_NAME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String wfnodeName;
    
    /**
     * 上级节点ID
     */
    @TableField(value = "PARENT_IDS", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String parentIds;
    
    /**
     * 工作流ID
     */
    @TableField(value = "WORKFLOW_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String workflowId;
    
    /**
     * 节点说明
     */
    @TableField(value = "NODE_RMK", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private String wfnodeRmk;
    
    /**
     * 节点层级
     */
    @TableField(value = "NODE_LEVEL", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private int wfnodeLevel;
    
    /**
     * 通过人数 0-所有人通过、N-n个人通过
     */
    @TableField(value = "AGREE_NUM", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Integer agreeNum;
    
    /**
     * 不通过人数 0-所有人不通过、N-n个人不通过
     */
    @TableField(value = "DISAGREE_NUM", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private Integer disagreeNum;

    /**
     * 创建时间
     */
    @TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @TableField(value = "MODIFY_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime modifyTime;
      
    @Override  
    public SysWorkflowNodeInfo clone() {  
    	SysWorkflowNodeInfo o = null;  
        try{  
            o = (SysWorkflowNodeInfo)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }
}