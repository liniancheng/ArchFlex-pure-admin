package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowInstanceNodeAuth;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-07-02 10:06:37
 */
@Data
public class SysWorkflowInstanceNodeAuthQuery extends Page<SysWorkflowInstanceNodeAuth> {

        /**
     * 权限ID
     */
    private String authId;
        /**
     * 权限类型 role-角色，user-用户
     */
    private String authType;
        /**
     * 对象ID
     */
    private String objId;
        /**
     * 节点ID
     */
    private String nodeId;
        /**
     * 创建时间
     */
    private Date createTime;
    
}
