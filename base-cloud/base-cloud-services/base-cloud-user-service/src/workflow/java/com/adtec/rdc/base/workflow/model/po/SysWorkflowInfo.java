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
 * 工作流定义信息
 * </p>
 *
 * @author adtec
 * @date 2020-06-30 09:15:26
 */
@Data
@Accessors(chain = true)
public class SysWorkflowInfo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工作流ID
	 */
	@TableId(value = "WORKFLOW_ID", type = IdType.UUID)
	private String workflowId;
	/**
	 * 工作流标识
	 */
	@TableField(value = "WORKFLOW_CODE", strategy = FieldStrategy.IGNORED)
	private String workflowCode;
	/**
	 * 工作流名称
	 */
	@TableField(value = "WORKFLOW_NAME", strategy = FieldStrategy.IGNORED)
	private String workflowName;
	/**
	 * 版本号
	 */
	@TableField(value = "VERSION_NUM", strategy = FieldStrategy.IGNORED)
	private String versionNum;
	/**
	 * 最大版本标志
	 */
	@TableField(value = "VERSION_MAX", strategy = FieldStrategy.IGNORED)
	private String versionMax;
	/**
	 * 工作流描述
	 */
	@TableField(value = "WORKFLOW_RMK", strategy = FieldStrategy.IGNORED)
	private String workflowRmk;
	/**
	 * 工作流级别
	 */
	@TableField(value = "WORKFLOW_LEVEL", strategy = FieldStrategy.IGNORED)
	private Integer workflowLevel;
	/**
	 * 工作流类型ID
	 */
	@TableField(value = "TYPE_ID", strategy = FieldStrategy.IGNORED)
	private String typeId;
	/**
	 * 流程平台ID
	 */
	@TableField(value = "WF_ID", strategy = FieldStrategy.IGNORED)
	private String wfId;
	/**
	 * 租户ID
	 */
	@TableField(value = "APP_ID", strategy = FieldStrategy.IGNORED)
	private String appId;
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
	/**
	 * 实例详情地址
	 */
	@TableField(value = "DETAIL_URL", strategy = FieldStrategy.IGNORED)
	private String detailUrl;
	/**
	 * 结束回调服务
	 */
	@TableField(value = "FINISH_SERVICE", strategy = FieldStrategy.IGNORED)
	private String finishService;

	@Override  
    public SysWorkflowInfo clone() {  
		SysWorkflowInfo o = null;  
        try{  
            o = (SysWorkflowInfo)super.clone();  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
    }
}