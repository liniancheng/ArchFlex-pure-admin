package com.adtec.rdc.base.workflow.model.bo;

import java.util.List;

import lombok.Data;

@Data
public class SysWorkflowMacroTree {
	/**
     * 子节点
     */
    private List<SysWorkflowMacroTree> children;
    /**
     * 宏变量ID
     */
    private String macroId;
    /**
     * 宏变量名称
     */
    private String macroName;
    /**
     * 宏变量标识
     */
    private String macroCode;
    /**
     * 宏变量描述
     */
    private String macroRmk;
    /**
     * 宏变量类型
     */
    private String macroType;
}
