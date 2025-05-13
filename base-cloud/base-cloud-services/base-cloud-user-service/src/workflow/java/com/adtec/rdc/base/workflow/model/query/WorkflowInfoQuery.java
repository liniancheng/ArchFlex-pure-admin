package com.adtec.rdc.base.workflow.model.query;

import java.util.List;

import com.adtec.rdc.base.workflow.model.bo.Workflow;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

@Data
public class WorkflowInfoQuery extends Page<Workflow> {
	/**
     * 应用ID
     */
    private String appId;
	/**
     * 登录名
     */
    private String loginName;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 角色IDs
     */
    private List<String> roleIds;
    
    private String status0;//是否查询 未处理任务
    private String status1;//是否查询 已处理任务
    
    private List<String> taskExtUndoSqls;//扩展任务待处理sqls
    private List<String> taskExtDoneSqls;//扩展任务已处理sqls
}
