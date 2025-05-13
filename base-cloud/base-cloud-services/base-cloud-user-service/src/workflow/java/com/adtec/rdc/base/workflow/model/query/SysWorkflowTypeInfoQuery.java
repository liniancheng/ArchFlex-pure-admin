package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowTypeInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-06-30 09:13:45
 */
@Data
public class SysWorkflowTypeInfoQuery extends Page<SysWorkflowTypeInfo> {

    /**
     * 工作流类型ID
     */
    private String typeId;
    /**
     * 工作流类型名称
     */
    private String typeName;
    /**
     * 工作流类型描述
     */
    private String typeRmk;
    /**
     * 租户ID
     */
    private String appId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date modifyTime;
    /**
     * 类型排序
     */
    private Integer typeOrder;
}
