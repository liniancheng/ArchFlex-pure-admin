package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstance;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:15:15
 */
@Data
public class SysWorkflowInstanceQuery extends Page<SysWorkflowInstance> {

        /**
     * 实例ID
     */
    private String instanceId;
        /**
     * 工作流ID
     */
    private String workflowId;
        /**
     * 实例名称
     */
    private String instanceName;
        /**
     * 实例说明
     */
    private String instanceRmk;
        /**
     * 实例状态 0-未处理 1-处理中 2-已完成
     */
    private String instanceStatus;
        /**
     * 创建用户
     */
    private String createUser;
        /**
     * 创建时间
     */
    private Date createTime;
        /**
     * 更新时间
     */
    private Date modifyTime;
    /**
     * 租户ID
     */
    private String appId;
}
