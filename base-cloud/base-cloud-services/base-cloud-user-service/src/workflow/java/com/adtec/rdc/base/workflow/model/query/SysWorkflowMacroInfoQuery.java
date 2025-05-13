package com.adtec.rdc.base.workflow.model.query;

import java.util.Date;

import com.adtec.rdc.base.workflow.model.po.SysWorkflowMacroInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * @author adtec
 * @date 2020-07-05 07:55:01
 */
@Data
public class SysWorkflowMacroInfoQuery extends Page<SysWorkflowMacroInfo> {

        /**
     * 宏变量ID
     */
    private String macroId;
        /**
     * 宏变量标识
     */
    private String macroCode;
        /**
     * 宏变量名称
     */
    private String macroName;
        /**
     * 宏变量类型 workflow/node/auth
     */
    private String macroType;
        /**
     * 工作流ID
     */
    private String workflowId;
        /**
     * 宏变量说明
     */
    private String macroRmk;
        /**
     * 创建时间
     */
    private Date createTime;
        /**
     * 更新时间
     */
    private Date modifyTime;
    
}
