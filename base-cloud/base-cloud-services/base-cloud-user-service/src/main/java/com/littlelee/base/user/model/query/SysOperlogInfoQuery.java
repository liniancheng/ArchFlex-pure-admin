package com.littlelee.base.user.model.query;

import com.littlelee.base.user.model.po.SysOperlogInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author: littlelee
 * @date: 2018/11/1 17:20
 */
@Data
public class SysOperlogInfoQuery extends Page<SysOperlogInfo> {
	
	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String logId;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 操作状态 1 失败  0 成功
     */
    private String logStatus;

    /**
     * 创建者
     */
    private String createBy;

}
