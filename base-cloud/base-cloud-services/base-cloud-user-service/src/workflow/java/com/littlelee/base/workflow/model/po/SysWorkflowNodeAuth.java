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
 * 工作流节点权限
 * </p>
 *
 * @author littlelee
 * @date 2020-06-30 09:14:34
 */
@Data
@Accessors(chain = true)
public class SysWorkflowNodeAuth implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限ID
	 */
	@TableId(value = "AUTH_ID", type = IdType.ASSIGN_UUID)
	private String authId;
	/**
	 * 权限类型 role-角色，user-用户
	 */
	@TableField(value = "AUTH_TYPE", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String authType;
	/**
	 * 对象ID
	 */
	@TableField(value = "OBJ_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String objId;
	/**
	 * 节点ID
	 */
	@TableField(value = "NODE_ID", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private String nodeId;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", insertStrategy = FieldStrategy.IGNORED, updateStrategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;

	@TableField(exist = false)
	private String objName;
	
	@Override  
    public SysWorkflowNodeAuth clone() {  
		SysWorkflowNodeAuth o = null;  
        try{  
            o = (SysWorkflowNodeAuth)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }
}