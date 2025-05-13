package com.adtec.rdc.base.workflow.model.po;

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
 * 工作流宏变量信息
 * </p>
 *
 * @author adtec
 * @date 2020-07-05 07:55:01
 */
@Data
@Accessors(chain = true)
public class SysWorkflowMacroInfo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 宏变量ID
	 */
	@TableId(value = "MACRO_ID", type = IdType.UUID)
	private String macroId;

	/**
	 * 宏变量标识
	 */
	@TableField(value = "MACRO_CODE", strategy = FieldStrategy.IGNORED)
	private String macroCode;
	/**
	 * 宏变量名称
	 */
	@TableField(value = "MACRO_NAME", strategy = FieldStrategy.IGNORED)
	private String macroName;
	/**
	 * 宏变量类型 workflow/node/auth
	 */
	@TableField(value = "MACRO_TYPE", strategy = FieldStrategy.IGNORED)
	private String macroType;
	/**
	 * 工作流ID
	 */
	@TableField(value = "WORKFLOW_ID", strategy = FieldStrategy.IGNORED)
	private String workflowId;
	/**
	 * 宏变量说明
	 */
	@TableField(value = "MACRO_RMK", strategy = FieldStrategy.IGNORED)
	private String macroRmk;
	/**
	 * 创建时间
	 */
	@TableField(value = "CREATE_TIME", strategy = FieldStrategy.IGNORED)
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "MODIFY_TIME", strategy = FieldStrategy.IGNORED)
	private LocalDateTime modifyTime;

	@Override  
    public SysWorkflowMacroInfo clone() {  
		SysWorkflowMacroInfo o = null;  
        try{  
            o = (SysWorkflowMacroInfo)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }
}