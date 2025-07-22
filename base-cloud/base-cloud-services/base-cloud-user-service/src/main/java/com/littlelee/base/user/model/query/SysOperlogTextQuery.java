package com.littlelee.base.user.model.query;

import com.littlelee.base.user.model.po.SysOperlogText;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * @author: littlelee
 * @date: 2018/11/1 17:20
 */
@Data
public class SysOperlogTextQuery extends Page<SysOperlogText> {
	
	private static final long serialVersionUID = 1L;
    private String logId;
    private String logName;
    private String logPath;
    private String logSize;
    private String logTxt;
}
